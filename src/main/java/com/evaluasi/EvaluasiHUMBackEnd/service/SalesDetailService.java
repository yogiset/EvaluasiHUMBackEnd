package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.SalesDetailDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Sales;
import com.evaluasi.EvaluasiHUMBackEnd.entity.SalesDetail;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.repository.SalesDetailRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SalesDetailService {
    private final SalesDetailRepository salesDetailRepository;
    private final SalesRepository salesRepository;

    public ResponseEntity<Object> createSalesDetail(SalesDetailDto salesDetailDto) {
        log.info("Inside CreateSalesDetail");
        try {
            Optional<Sales> optionalSales = salesRepository.findById(salesDetailDto.getIdsales());
            if(optionalSales.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sales not found for Id: " + salesDetailDto.getIdsales());
            }
            Sales sales = optionalSales.get();

            SalesDetail salesDetail = new SalesDetail();
            salesDetail.setSales(sales);
            salesDetail.setBulan(salesDetailDto.getBulan());
            salesDetail.setTargetbln(salesDetailDto.getTargetbln());
            salesDetail.setTercapaii(salesDetailDto.getTercapaii());
            double percent = (salesDetail.getTercapaii() * 100.0) / salesDetail.getTargetbln();
            salesDetail.setTercapaipersenn(String.format("%.2f%%",percent));

            salesDetailRepository.save(salesDetail);

            double totalTercapaii = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getTercapaii)
                    .sum();

            sales.setTercapai((int) totalTercapaii);

            double overallPercentage = (totalTercapaii * 100.0) / (sales.getTarget());

            sales.setTercapaipersen(String.format("%.2f%%", overallPercentage));
            salesRepository.save(sales);

            return ResponseEntity.ok("New Sales Detail added successfully");

        } catch (Exception e) {
            log.error("Error creating new sales detail", e);
            return ResponseEntity.status(500).body("Error creating new sales detail");
        }
    }

    public ResponseEntity<Object> editSalesDetail(Long id, SalesDetailDto salesDetailDto) {
        log.info("Inside EditSalesDetail");
        try {
            Optional<SalesDetail> optionalSalesDetail = salesDetailRepository.findById(id);
            if(optionalSalesDetail.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SalesDetail not found for Id: " + id);
            }
            SalesDetail salesDetail = optionalSalesDetail.get();
            Sales sales = salesDetail.getSales();

            salesDetail.setBulan(salesDetailDto.getBulan());
            salesDetail.setTargetbln(salesDetailDto.getTargetbln());
            salesDetail.setTercapaii(salesDetailDto.getTercapaii());
            double percent = (salesDetail.getTercapaii() * 100.0) / salesDetail.getTargetbln();
            salesDetail.setTercapaipersenn(String.format("%.2f%%",percent));

            salesDetailRepository.save(salesDetail);

            double totalTercapaii = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getTercapaii)
                    .sum();

            sales.setTercapai((int)totalTercapaii);

            double overallPercentage = (totalTercapaii * 100.0) / (sales.getTarget());
            sales.setTercapaipersen(String.format("%.2f%%", overallPercentage));
            salesRepository.save(sales);

            return ResponseEntity.ok("Sales Detail edited successfully");

        } catch (Exception e) {
            log.error("Error editing sales detail", e);
            return ResponseEntity.status(500).body("Error editing sales detail");
        }
    }


    public ResponseEntity<Object> deleteSalesDetail(Long id) {
        log.info("Inside DeleteSalesDetail");
        try {
            Optional<SalesDetail> optionalSalesDetail = salesDetailRepository.findById(id);

            if (optionalSalesDetail.isPresent()) {
                SalesDetail salesDetail = optionalSalesDetail.get();
                Sales sales = salesDetail.getSales();

                salesDetailRepository.deleteById(id);

                double totalTercapaii = sales.getSalesDetails().stream()
                        .mapToDouble(SalesDetail::getTercapaii)
                        .sum();

                sales.setTercapai((int) totalTercapaii);

                double overallPercentage = (totalTercapaii * 100.0) / (sales.getTarget());
                sales.setTercapaipersen(String.format("%.2f%%", overallPercentage));

                salesRepository.save(sales);

                return ResponseEntity.ok("Successfully deleted sales detail");
            } else {
                return ResponseEntity.status(404).body("Sales detail not found");
            }
        } catch (Exception e) {
            log.error("Error deleting sales detail", e);
            return ResponseEntity.status(500).body("Error deleting sales detail");
        }
    }



    public SalesDetailDto fetchSalesDetailDtoById(Long id) throws AllException {
        log.info("Inside fetchSalesDetailDtoById");

        SalesDetail salesDetail = salesDetailRepository.findById(id)
                .orElseThrow(() -> new AllException("Sales detail with id " + id + " not found"));

        SalesDetailDto salesDetailDto = new SalesDetailDto();
        Sales sales = salesDetail.getSales();
        salesDetailDto.setId(salesDetail.getId());
        salesDetailDto.setBulan(salesDetail.getBulan());
        salesDetailDto.setTargetbln(salesDetail.getTargetbln());
        salesDetailDto.setTercapaii(salesDetail.getTercapaii());
        salesDetailDto.setTercapaipersenn(salesDetail.getTercapaipersenn());
        salesDetailDto.setIdsales(sales.getIdsales());

        return salesDetailDto;
    }
}
