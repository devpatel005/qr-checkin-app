package com.QRcode.qrcheckin.Package;

import com.QRcode.qrcheckin.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface QrCodeRepository extends JpaRepository<QrCode, Long> {
    QrCode findByid(Long id); // Corrected method name


}
