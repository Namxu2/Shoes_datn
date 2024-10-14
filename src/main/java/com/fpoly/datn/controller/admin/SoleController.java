package com.fpoly.datn.controller.admin;

import com.fpoly.datn.entity.Sole;
import com.fpoly.datn.entity.User;
import com.fpoly.datn.model.mapper.SoleMapper;
import com.fpoly.datn.model.request.CreateSoleRequest;
import com.fpoly.datn.security.CustomUserDetails;
import com.fpoly.datn.service.ImageService;
import com.fpoly.datn.service.SoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SoleController {
    @Autowired
    private SoleService soleService;
    @Autowired
    private ImageService imageService;
    @GetMapping("/admin/soles")
    public String homePage(Model model,
                           @RequestParam(defaultValue = "", required = false) String id,
                           @RequestParam(defaultValue = "", required = false) String name,
                           @RequestParam(defaultValue = "", required = false) String status,
                           @RequestParam(defaultValue = "1", required = false) Integer page){
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<String> images = imageService.getListImageOfUser(user.getId());
        model.addAttribute("images", images);
        Page<Sole> soles = soleService.adminGetListSoles(id, name, status, page);
        model.addAttribute("sole", soles.getContent());
        model.addAttribute("totalPages", soles.getTotalPages());
        model.addAttribute("currentPage", soles.getPageable().getPageNumber() + 1);
        return "admin/sole/list";
    }
    @PostMapping("api/admin/soles")
    public ResponseEntity<Object> createSole(@Valid @RequestBody CreateSoleRequest createSoleRequest){
        Sole sole = soleService.createSole(createSoleRequest);
        return ResponseEntity.ok(SoleMapper.toSoleDTO(sole));
    }
    @PutMapping("/api/admin/soles/{id}")
    public ResponseEntity<Object> updateSole(@Valid @RequestBody CreateSoleRequest createSoleRequest, @PathVariable long id){
        soleService.updateSole(createSoleRequest, id);
        return ResponseEntity.ok("Sửa đế giày thành công!");
    }
    @DeleteMapping("/api/admin/soles/{id}")
        public ResponseEntity<Object> deleteSole(@PathVariable long id){
            soleService.deleteSole(id);
            return ResponseEntity.ok("Xóa đế giày thành công!");

    }
    @GetMapping("/api/admin/soles/{id}")
    public ResponseEntity<Object> getSoleById(@PathVariable long id){
        Sole sole = soleService.getSoleById(id);
        return ResponseEntity.ok(sole);
    }
}
