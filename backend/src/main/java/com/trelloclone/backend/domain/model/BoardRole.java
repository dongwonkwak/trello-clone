package com.trelloclone.backend.domain.model;

public enum BoardRole {
    ADMIN,  // 모든 권한을 가짐 (보드 설정 변경, 멤버 관리, 카드 관리 등)
    MEMBER, // 카드 생성/수정/이동 등의 작업 권한을 가짐
    VIEWER  // 읽기 전용 접근 권한
}
