package com.elyashevich.image.service.impl;

import com.elyashevich.image.domain.entity.ImageMetadata;
import com.elyashevich.image.domain.event.EventType;
import com.elyashevich.image.domain.event.NotificationEvent;
import com.elyashevich.image.exception.UploadingException;
import com.elyashevich.image.kafka.NotificationProducer;
import com.elyashevich.image.repository.ImageMetadataRepository;
import com.elyashevich.image.service.ImageService;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final GridFsTemplate gridFsTemplate;
    private final ImageMetadataRepository imageMetadataRepository;
    private final NotificationProducer notificationProducer;

    @Override
    public GridFSFindIterable findAll() {
        return this.gridFsTemplate.find(null);
    }

    @Transactional
    @Override
    public List<GridFSFile> findByOwnerId(final String userId) {
        var imageMetadata = imageMetadataRepository.findByOwnerId(userId);
        var data = new ArrayList<GridFSFile>();
        imageMetadata.forEach(image -> {
            data.add(this.gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(image.getId()))));
        });
        return data;
    }

    @Override
    public byte[] findById(final String id) {
        var gridFsFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(id)));
        byte[] bytes = null;
        GridFsResource resource = gridFsTemplate.getResource(gridFsFile);
        try (var inputStream = resource.getInputStream()) {
            bytes = inputStream.readAllBytes();
        } catch (IOException e) {
            throw new UploadingException(e);
        }
        return bytes;
    }

    @Transactional
    @Override
    public ImageMetadata create(final MultipartFile file, final String userId) {
        try {
            this.notificationProducer.sendOrderEvent(NotificationEvent.builder()
                    .eventType(EventType.PENDING)
                    .build());

            var id = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
            var image = ImageMetadata.builder()
                    .id(id.toString())
                    .filename(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .ownerId(userId)
                    .uploadDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                    .build();

            var result = this.imageMetadataRepository.save(image);

            this.notificationProducer.sendOrderEvent(NotificationEvent.builder()
                    .eventType(EventType.FULFILLED)
                    .build());

            return result;
        } catch (IOException e) {
            notificationProducer.sendOrderEvent(NotificationEvent.builder()
                    .eventType(EventType.REJECTED)
                    .build());
            throw new UploadingException("Error uploading file", e);
        }
    }

    @Override
    public String upload(final MultipartFile file) {
        String id = null;
        try {
            var imageId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
            id = imageId.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }
}
