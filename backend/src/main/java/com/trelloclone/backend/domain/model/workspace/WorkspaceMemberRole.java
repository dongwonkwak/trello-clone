package com.trelloclone.backend.domain.model.workspace;

public enum WorkspaceMemberRole {
    ADMIN,    // 관리자 권한 - 워크스페이스 설정 변경, 멤버 관리 등
    MEMBER,   // 일반 멤버 권한 - 보드 생성, 수정 등
    VIEWER    // 조회 권한 - 읽기만 가능
}