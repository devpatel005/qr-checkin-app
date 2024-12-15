package com.QRcode.qrcheckin.Package;
import java.time.LocalDateTime;
import java.util.*;
import com.QRcode.qrcheckin.QrCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QrCodeService {
    @Autowired
    private QrCodeRepository qrCodeRepository;

    public QrCode getQrCodeByQrId(Long qrId) {
        return qrCodeRepository.findByid(qrId); // Correctly calls the method
    }

    public void updateQrCode(QrCode qrCode) {
        qrCodeRepository.save(qrCode); // This will handle both insert and update
    }

    public List<QrCode> getAllQrCodes() {
        return qrCodeRepository.findAll();
    }

}

