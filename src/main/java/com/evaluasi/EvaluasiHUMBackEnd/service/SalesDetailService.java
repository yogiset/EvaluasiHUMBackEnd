package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.SalesDetailDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
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

            Karyawan karyawan = sales.getKaryawan();

            // Check if a SalesDetail for the same bulan, tahun, and nik already exists
            boolean bulanExists = salesDetailRepository.existsByBulanAndSales_TahunAndSales_Karyawan_Nik(
                    salesDetailDto.getBulan(),
                    sales.getTahun(),
                    karyawan.getNik()
            );
            if (bulanExists) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Sales detail for the same month, year, and NIK already exists.");
            }

            salesDetail.setBulan(salesDetailDto.getBulan());
            salesDetail.setJumlahvisit(salesDetailDto.getJumlahvisit());

            salesDetail.setTargetblntotal(salesDetailDto.getTargetblntotal());
            salesDetail.setTercapaiitotal(salesDetailDto.getTercapaiitotal());
            double percenttotal = (salesDetail.getTercapaiitotal() * 100.0) / salesDetail.getTargetblntotal();
            salesDetail.setTercapaipersenntotal(String.format("%.2f%%",percenttotal));

            salesDetail.setTargetblngadus(salesDetailDto.getTargetblngadus());
            salesDetail.setTercapaiigadus(salesDetailDto.getTercapaiigadus());
            double percentgadus = (salesDetail.getTercapaiigadus() * 100.0) / salesDetail.getTargetblngadus();
            salesDetail.setTercapaipersenngadus(String.format("%.2f%%",percentgadus));

            salesDetail.setTargetblnpremium(salesDetailDto.getTargetblnpremium());
            salesDetail.setTercapaiipremium(salesDetailDto.getTercapaiipremium());
            double percentpremium = (salesDetail.getTercapaiipremium() * 100.0) / salesDetail.getTargetblnpremium();
            salesDetail.setTercapaipersennpremium(String.format("%.2f%%",percentpremium));

            salesDetailRepository.save(salesDetail);

//            // Sum the target values from all SalesDetail entries and cast to int
//            int totalTargetTotal = (int) sales.getSalesDetails().stream()
//                    .mapToDouble(SalesDetail::getTargetblntotal)
//                    .sum();
//            int totalTargetGadus = (int) sales.getSalesDetails().stream()
//                    .mapToDouble(SalesDetail::getTargetblngadus)
//                    .sum();
//            int totalTargetPremium = (int) sales.getSalesDetails().stream()
//                    .mapToDouble(SalesDetail::getTargetblnpremium)
//                    .sum();
//
//            // Set the aggregated target values in the Sales object
//            sales.setTargettotal(totalTargetTotal);
//            sales.setTargetgadus(totalTargetGadus);
//            sales.setTargetpremium(totalTargetPremium);


            double totalTercapaiitotal = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getTercapaiitotal)
                    .sum();

            sales.setTercapaitotal((int) totalTercapaiitotal);

            double overallPercentagetotal = (totalTercapaiitotal * 100.0) / (sales.getTargettotal());

            sales.setTercapaipersentotal(overallPercentagetotal);



            double totalTercapaiigadus = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getTercapaiigadus)
                    .sum();

            sales.setTercapaigadus((int) totalTercapaiigadus);

            double overallPercentagegadus = (totalTercapaiigadus * 100.0) / (sales.getTargetgadus());

            sales.setTercapaipersengadus(overallPercentagegadus);


            double totalTercapaiipremium = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getTercapaiipremium)
                    .sum();

            sales.setTercapaipremium((int) totalTercapaiipremium);

            double overallPercentagepremium = (totalTercapaiipremium * 100.0) / (sales.getTargetpremium());

            sales.setTercapaipersenpremium(overallPercentagepremium);


            double totalVisit = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getJumlahvisit)
                    .sum();

            if ( totalVisit > 0){
                int numberOfDetails = sales.getSalesDetails().size();
                double averageVisit = numberOfDetails > 0 ? totalVisit / numberOfDetails : 0;
                sales.setJumlahvisit(averageVisit);
            } else {
                sales.setJumlahvisit(sales.getJumlahvisit());
            }

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
            salesDetail.setJumlahvisit(salesDetailDto.getJumlahvisit());

            salesDetail.setTargetblntotal(salesDetailDto.getTargetblntotal());
            salesDetail.setTercapaiitotal(salesDetailDto.getTercapaiitotal());
            double percenttotal = (salesDetail.getTercapaiitotal() * 100.0) / salesDetail.getTargetblntotal();
            salesDetail.setTercapaipersenntotal(String.format("%.2f%%",percenttotal));

            salesDetail.setTargetblngadus(salesDetailDto.getTargetblngadus());
            salesDetail.setTercapaiigadus(salesDetailDto.getTercapaiigadus());
            double percentgadus = (salesDetail.getTercapaiigadus() * 100.0) / salesDetail.getTargetblngadus();
            salesDetail.setTercapaipersenngadus(String.format("%.2f%%",percentgadus));

            salesDetail.setTargetblnpremium(salesDetailDto.getTargetblnpremium());
            salesDetail.setTercapaiipremium(salesDetailDto.getTercapaiipremium());
            double percentpremium = (salesDetail.getTercapaiipremium() * 100.0) / salesDetail.getTargetblnpremium();
            salesDetail.setTercapaipersennpremium(String.format("%.2f%%",percentpremium));

            salesDetailRepository.save(salesDetail);

//            // Sum the target values from all SalesDetail entries and cast to int
//            int totalTargetTotal = (int) sales.getSalesDetails().stream()
//                    .mapToDouble(SalesDetail::getTargetblntotal)
//                    .sum();
//            int totalTargetGadus = (int) sales.getSalesDetails().stream()
//                    .mapToDouble(SalesDetail::getTargetblngadus)
//                    .sum();
//            int totalTargetPremium = (int) sales.getSalesDetails().stream()
//                    .mapToDouble(SalesDetail::getTargetblnpremium)
//                    .sum();
//
//            // Set the aggregated target values in the Sales object
//            sales.setTargettotal(totalTargetTotal);
//            sales.setTargetgadus(totalTargetGadus);
//            sales.setTargetpremium(totalTargetPremium);

            double totalTercapaiitotal = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getTercapaiitotal)
                    .sum();

            sales.setTercapaitotal((int) totalTercapaiitotal);

            double overallPercentagetotal = (totalTercapaiitotal * 100.0) / (sales.getTargettotal());

            sales.setTercapaipersentotal(overallPercentagetotal);



            double totalTercapaiigadus = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getTercapaiigadus)
                    .sum();

            sales.setTercapaigadus((int) totalTercapaiigadus);

            double overallPercentagegadus = (totalTercapaiigadus * 100.0) / (sales.getTargetgadus());

            sales.setTercapaipersengadus(overallPercentagegadus);


            double totalTercapaiipremium = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getTercapaiipremium)
                    .sum();

            sales.setTercapaipremium((int) totalTercapaiipremium);

            double overallPercentagepremium = (totalTercapaiipremium * 100.0) / (sales.getTargetpremium());

            sales.setTercapaipersenpremium(overallPercentagepremium);


            double totalVisit = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getJumlahvisit)
                    .sum();

            if ( totalVisit > 0){
                int numberOfDetails = sales.getSalesDetails().size();
                double averageVisit = numberOfDetails > 0 ? totalVisit / numberOfDetails : 0;
                sales.setJumlahvisit(averageVisit);
            } else {
                sales.setJumlahvisit(0);
            }


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

//                // Sum the target values from all SalesDetail entries and cast to int
//                int totalTargetTotal = (int) sales.getSalesDetails().stream()
//                        .mapToDouble(SalesDetail::getTargetblntotal)
//                        .sum();
//                int totalTargetGadus = (int) sales.getSalesDetails().stream()
//                        .mapToDouble(SalesDetail::getTargetblngadus)
//                        .sum();
//                int totalTargetPremium = (int) sales.getSalesDetails().stream()
//                        .mapToDouble(SalesDetail::getTargetblnpremium)
//                        .sum();
//
//                // Set the aggregated target values in the Sales object
//                sales.setTargettotal(totalTargetTotal);
//                sales.setTargetgadus(totalTargetGadus);
//                sales.setTargetpremium(totalTargetPremium);

                double totalTercapaiitotal = sales.getSalesDetails().stream()
                        .mapToDouble(SalesDetail::getTercapaiitotal)
                        .sum();

                sales.setTercapaitotal((int) totalTercapaiitotal);

                double overallPercentagetotal = (totalTercapaiitotal * 100.0) / (sales.getTargettotal());

                sales.setTercapaipersentotal(overallPercentagetotal);



                double totalTercapaiigadus = sales.getSalesDetails().stream()
                        .mapToDouble(SalesDetail::getTercapaiigadus)
                        .sum();

                sales.setTercapaigadus((int) totalTercapaiigadus);

                double overallPercentagegadus = (totalTercapaiigadus * 100.0) / (sales.getTargetgadus());

                sales.setTercapaipersengadus(overallPercentagegadus);


                double totalTercapaiipremium = sales.getSalesDetails().stream()
                        .mapToDouble(SalesDetail::getTercapaiipremium)
                        .sum();

                sales.setTercapaipremium((int) totalTercapaiipremium);

                double overallPercentagepremium = (totalTercapaiipremium * 100.0) / (sales.getTargetpremium());

                sales.setTercapaipersenpremium(overallPercentagepremium);


                double totalVisit = sales.getSalesDetails().stream()
                        .mapToDouble(SalesDetail::getJumlahvisit)
                        .sum();

                if ( totalVisit > 0){
                    int numberOfDetails = sales.getSalesDetails().size();
                    double averageVisit = numberOfDetails > 0 ? totalVisit / numberOfDetails : 0;
                    sales.setJumlahvisit(averageVisit);
                } else {
                    sales.setJumlahvisit(0);
                }

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

        salesDetailDto.setTargetblntotal(salesDetail.getTargetblntotal());
        salesDetailDto.setTercapaiitotal(salesDetail.getTercapaiitotal());
        salesDetailDto.setTercapaipersenntotal(salesDetail.getTercapaipersenntotal());

        salesDetailDto.setTargetblngadus(salesDetail.getTargetblngadus());
        salesDetailDto.setTercapaiigadus(salesDetail.getTercapaiigadus());
        salesDetailDto.setTercapaipersenngadus(salesDetail.getTercapaipersenngadus());

        salesDetailDto.setTargetblnpremium(salesDetail.getTargetblnpremium());
        salesDetailDto.setTercapaiipremium(salesDetail.getTercapaiipremium());
        salesDetailDto.setTercapaipersennpremium(salesDetail.getTercapaipersennpremium());

        salesDetailDto.setJumlahvisit(salesDetail.getJumlahvisit());
        salesDetailDto.setIdsales(sales.getIdsales());

        return salesDetailDto;
    }
}
