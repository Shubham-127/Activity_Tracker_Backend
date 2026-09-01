package com.example.activity_tracker_monitor.controller;

import com.example.activity_tracker_monitor.dto.ActivitySummaryResponse;
import com.example.activity_tracker_monitor.security.AuthUtil;
import com.example.activity_tracker_monitor.serviceImpl.ActivitySummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/activity")
@RequiredArgsConstructor
public class AdminActivityController {

    private final ActivitySummaryService summaryService;

    @GetMapping("/employee/{id}")
    @PreAuthorize("hasRole('ADMIN') or @teamGuard.isManagerOf(authentication, #id)")
    public ResponseEntity<List<ActivitySummaryResponse>> getEmployeeActivity(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(summaryService.getSummary(id, from, to));
    }

    @GetMapping("/team")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<ActivitySummaryResponse>> getTeamActivity(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            Authentication auth) {
        Long managerId = AuthUtil.currentEmployeeId(auth);
        if (managerId == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(summaryService.getTeamSummary(managerId, from, to));
    }
}
