package fast_food_rest.controller;

import fast_food_rest.entity.Attachment;
import fast_food_rest.entity.AttachmentContent;
import fast_food_rest.repository.AttachmentContentRepository;
import fast_food_rest.repository.AttachmentRepository;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
public class AttachmentController {

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    @GetMapping("/image/{id}")
    public void getFileFromDb(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> contentOptional = attachmentContentRepository.findByAttachmentId(id);
            if (contentOptional.isPresent()) {
                AttachmentContent attachmentContent = contentOptional.get();
                response.setContentType(attachment.getContentType());
                response.setContentLength(attachmentContent.getBytes().length); // Set the content length
                response.setHeader("Content-Disposition", "inline; filename=\"" + attachment.getFileOriginalName() + "\""); // Inline display
                response.getOutputStream().write(attachmentContent.getBytes());
                response.getOutputStream().flush();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND); // Not found error
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // Not found error
        }
    }
}

