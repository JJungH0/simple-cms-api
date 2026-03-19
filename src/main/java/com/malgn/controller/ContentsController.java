package com.malgn.controller;

import com.malgn.dto.content.*;
import com.malgn.service.ContentsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contents")
@RequiredArgsConstructor
public class ContentsController {
    private final ContentsService contentsService;

    @PostMapping
    public ResponseEntity<ContentsCreateResp> create(@RequestBody @Valid ContentsCreateReq req,
                                                     Authentication authentication) {
        ContentsCreateResp contents = contentsService.createContent(req, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(contents);
    }

    @GetMapping
    public ResponseEntity<Page<ContentListResp>> findAll(Pageable pageable) {
        return ResponseEntity.ok(contentsService.findAll(pageable));
    }

    @GetMapping("/popular")
    public ResponseEntity<Page<ContentListResp>> findPopularContents(
            @PageableDefault(sort = "viewCount",direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(contentsService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentInfoResp> findById(@PathVariable Long id) {
        return ResponseEntity.ok(contentsService.getContentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentInfoResp> update(@PathVariable Long id,
                                                  @RequestBody @Valid ContentUpdateReq req,
                                                  Authentication authentication) {
        return ResponseEntity.ok(contentsService.updateContent(id, req, authentication));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                    Authentication authentication) {
        contentsService.deleteContent(id,authentication);
        return ResponseEntity.noContent().build();
    }
}
