package com.QRcode.qrcheckin.Package;

import com.QRcode.qrcheckin.QrCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow all origins (or specify your mobile app's origin)
public class QrCodeController {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    @Autowired
    private QrCodeService qrCodeService;

    @PostMapping("/checkIn/{qr_id}")
    public ResponseEntity<?> checkIn(@PathVariable Long qr_id) {
        try {
            // Retrieve the QR code by ID
            QrCode qrCode = qrCodeService.getQrCodeByQrId(qr_id);

            // Check if the QR code exists
            if (qrCode == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: QR Code not found");
            }

            // Update the check-in status
            if (!"Checked In".equals(qrCode.getCheckInStatus())) {
                qrCode.setCheckInStatus("Checked In");
                qrCodeService.updateQrCode(qrCode);
            }

            // Return the updated QR code object along with 200 OK status
            return ResponseEntity.ok(qrCode);

        } catch (Exception e) {
            // Handle any unexpected exceptions and return a 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred - " + e.getMessage());
        }
    }

    @GetMapping("/qrcodes")
    public ResponseEntity<List<QrCode>> getAllQrCodes() {
        List<QrCode> qrCodes = qrCodeService.getAllQrCodes();
        return ResponseEntity.ok(qrCodes);
    }

    @PutMapping("/qrcodes/{index}")
    public ResponseEntity<?> updateQrCode(@PathVariable Long index, @RequestBody QrCode qrCodeDetails) {
        try {
            // Retrieve the existing QR code
            QrCode existingQrCode = qrCodeService.getQrCodeByQrId(index);
            if (existingQrCode == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: QR Code not found with id " + index);
            }

            // Update fields in the existing QR code
            existingQrCode.setFirstName(qrCodeDetails.getFirstName());
            existingQrCode.setMiddleName(qrCodeDetails.getMiddleName());
            existingQrCode.setLastName(qrCodeDetails.getLastName());
            existingQrCode.setGradeLvl(qrCodeDetails.getGradeLvl());
            existingQrCode.setWing(qrCodeDetails.getWing());
            existingQrCode.setFatherName(qrCodeDetails.getFatherName());
            existingQrCode.setMotherName(qrCodeDetails.getMotherName());
            existingQrCode.setFatherNum(qrCodeDetails.getFatherNum());
            existingQrCode.setMotherNum(qrCodeDetails.getMotherNum());
            existingQrCode.setFatherEmail(qrCodeDetails.getFatherEmail());
            existingQrCode.setMotherEmail(qrCodeDetails.getMotherEmail());
            existingQrCode.setMedInfo(qrCodeDetails.getMedInfo());
            existingQrCode.setCheckInStatus(qrCodeDetails.getCheckInStatus());

            // Save the updated QR code
            qrCodeService.updateQrCode(existingQrCode);

            // Return the updated QR code object along with a 200 OK status
            return ResponseEntity.ok(existingQrCode);

        } catch (Exception e) {
            // Handle any unexpected exceptions and return a 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred - " + e.getMessage());
        }
    }

    @PostMapping("/qrcodes/newdelegate")
    public ResponseEntity<?> createQrCode(@RequestBody QrCode qrCodeDetails) {
        try {
            // Create a new QR code object
            QrCode newQrCode = new QrCode();

            // Update fields in the new QR code
            newQrCode.setFirstName(qrCodeDetails.getFirstName());
            newQrCode.setMiddleName(qrCodeDetails.getMiddleName());
            newQrCode.setLastName(qrCodeDetails.getLastName());
            newQrCode.setGradeLvl(qrCodeDetails.getGradeLvl());
            newQrCode.setWing(qrCodeDetails.getWing());
            newQrCode.setFatherName(qrCodeDetails.getFatherName());
            newQrCode.setMotherName(qrCodeDetails.getMotherName());
            newQrCode.setFatherNum(qrCodeDetails.getFatherNum());
            newQrCode.setMotherNum(qrCodeDetails.getMotherNum());
            newQrCode.setFatherEmail(qrCodeDetails.getFatherEmail());
            newQrCode.setMotherEmail(qrCodeDetails.getMotherEmail());
            newQrCode.setMedInfo(qrCodeDetails.getMedInfo());
            newQrCode.setCheckInStatus(qrCodeDetails.getCheckInStatus());

            // Save the new QR code (assuming you have a repository)
            qrCodeService.updateQrCode(newQrCode); // Replace with your actual repository

            // Return the saved QR code object along with a 201 Created status
            return ResponseEntity.status(HttpStatus.CREATED).body(newQrCode);

        } catch (Exception e) {
            // Handle any unexpected exceptions and return a 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An unexpected error occurred - " + e.getMessage());
        }
    }





}
