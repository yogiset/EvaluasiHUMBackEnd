package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.*;
import com.evaluasi.EvaluasiHUMBackEnd.entity.*;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalesService {
    private final SalesRepository salesRepository;
    private final KaryawanRepository karyawanRepository;
    private final SalesDetailRepository salesDetailRepository;
    private final HimpunanKriteriaRepository himpunanKriteriaRepository;
    private final BobotKriteriaRepository bobotKriteriaRepository;

    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public ResponseEntity<Object> createSales(SalesDto salesDto, String nik) {
        log.info("inside createSales", salesDto);
        try {
            Sales sales = new Sales();
            Karyawan karyawan = karyawanRepository.findByNik(salesDto.getNik());
            if (karyawan == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Karyawan not found for NIK: " + salesDto.getNik());
            }
            // Check if the karyawan's jabatan is "Sales"
            if (!"Sales".equalsIgnoreCase(karyawan.getJabatan())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Karyawan with NIK: " + salesDto.getNik() + " is not a Sales");
            }

            // Check if a Sales record for the same nik and tahun already exists
            boolean salesExists = salesRepository.existsByKaryawanAndTahun(karyawan, salesDto.getTahun());
            if (salesExists) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Sales record for the same NIK and year already exists.");
            }
            sales.setKaryawan(karyawan);
            sales.setTahun(salesDto.getTahun());
            sales.setTargettotal(salesDto.getTargettotal());
            sales.setTargetgadus(salesDto.getTargetgadus());
            sales.setTargetpremium(salesDto.getTargetpremium());
            sales.setJumlahcustomer(salesDto.getJumlahcustomer());


            sales.setSalesDetails(new ArrayList<>());


            if (salesDto.getSalesDetailDtoList() != null) {
                for (SalesDetailDto salesDetailDto : salesDto.getSalesDetailDtoList()) {
                    SalesDetail salesDetail = new SalesDetail();
                    // Check if a SalesDetail for the same bulan, tahun, and nik already exists
                    boolean bulanExists = salesRepository.existsBySalesDetails_BulanAndTahunAndKaryawan_Nik(
                            salesDetailDto.getBulan(),
                            sales.getTahun(),
                            karyawan.getNik()
                    );
                    if (bulanExists) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body("Sales detail for the same month, year, and NIK already exists.");
                    }
                    salesDetail.setBulan(salesDetailDto.getBulan());
                    salesDetail.setTargetblntotal(salesDetailDto.getTargetblntotal());
                    salesDetail.setTargetblngadus(salesDetailDto.getTargetblngadus());
                    salesDetail.setTargetblnpremium(salesDetailDto.getTargetblnpremium());
                    salesDetail.setTercapaiitotal(salesDetailDto.getTercapaiitotal());
                    salesDetail.setTercapaiigadus(salesDetailDto.getTercapaiigadus());
                    salesDetail.setTercapaiipremium(salesDetailDto.getTercapaiipremium());

                    double percenttotal = (salesDetail.getTercapaiitotal() * 100.0) / salesDetail.getTargetblntotal();
                    salesDetail.setTercapaipersenntotal(roundToTwoDecimalPlaces(percenttotal));
                    double percentgadus = (salesDetail.getTercapaiigadus() * 100.0) / salesDetail.getTargetblngadus();
                    salesDetail.setTercapaipersenngadus(roundToTwoDecimalPlaces(percentgadus));
                    double percentpremium = (salesDetail.getTercapaiipremium() * 100.0) / salesDetail.getTargetblnpremium();
                    salesDetail.setTercapaipersennpremium(roundToTwoDecimalPlaces(percentpremium));

                    salesDetail.setJumlahvisit(salesDetailDto.getJumlahvisit());

                    salesDetail.setSales(sales);
                    salesDetailRepository.save(salesDetail);

                    // Add the created SalesDetail object to the salesDetails list
                    sales.getSalesDetails().add(salesDetail);
                }
            }
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

            double overallPercentagetotal = (totalTercapaiitotal * 100.0) / sales.getTargettotal();
            sales.setTercapaipersentotal(roundToTwoDecimalPlaces(overallPercentagetotal));



            double totalTercapaiigadus = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getTercapaiigadus)
                    .sum();

            sales.setTercapaigadus((int) totalTercapaiigadus);

            double overallPercentagegadus = (totalTercapaiigadus * 100.0) / sales.getTargetgadus();
            sales.setTercapaipersengadus(roundToTwoDecimalPlaces(overallPercentagegadus));




            double totalTercapaiipremium = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getTercapaiipremium)
                    .sum();

            sales.setTercapaipremium((int) totalTercapaiipremium);

            double overallPercentagepremium = (totalTercapaiipremium * 100.0) / sales.getTargetpremium();
            sales.setTercapaipersenpremium(roundToTwoDecimalPlaces(overallPercentagepremium));



            double totalVisit = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getJumlahvisit)
                    .sum();

            if ( totalVisit > 0){
                int numberOfDetails = sales.getSalesDetails().size();
                double averageVisit = numberOfDetails > 0 ? totalVisit / numberOfDetails : 0;
                sales.setJumlahvisit(averageVisit);
            } else {
                sales.setJumlahvisit(salesDto.getJumlahvisit());
            }

            salesRepository.save(sales);

            return ResponseEntity.ok("New data Sales added successfully");

        } catch (Exception e) {
            log.error("Error creating new data sales", e);
            return ResponseEntity.status(500).body("Error creating new data sales");
        }
    }

    public ResponseEntity<Object> editSales(Long id, SalesDto salesDto) {
        try {
            log.info("Inside edit sales");
            Optional<Sales> optionalSales = salesRepository.findById(id);
            if (!optionalSales.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sales not found with id " + id);
            }
            Sales sales = optionalSales.get();
            sales.setTahun(salesDto.getTahun());
            sales.setTargettotal(salesDto.getTargettotal());
            sales.setTargetgadus(salesDto.getTargetgadus());
            sales.setTargetpremium(salesDto.getTargetpremium());
            sales.setJumlahcustomer(salesDto.getJumlahcustomer());

            double totalTercapaiitotal = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getTercapaiitotal)
                    .sum();

            if (totalTercapaiitotal > 0) {
                sales.setTercapaitotal((int) totalTercapaiitotal);
            } else {
                sales.setTercapaitotal(salesDto.getTercapaitotal());
            }

            if (totalTercapaiitotal > 0){
                double overallPercentagetotal = (totalTercapaiitotal * 100.0) / sales.getTargettotal();
                sales.setTercapaipersentotal(roundToTwoDecimalPlaces(overallPercentagetotal));
            } else {
                double overallPercentage = (salesDto.getTercapaitotal() * 100.0) / sales.getTargettotal();
                sales.setTercapaipersentotal(roundToTwoDecimalPlaces(overallPercentage));
            }



            double totalTercapaiigadus = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getTercapaiigadus)
                    .sum();

            if (totalTercapaiigadus > 0) {
                sales.setTercapaigadus((int) totalTercapaiigadus);
            } else {
                sales.setTercapaigadus(salesDto.getTercapaigadus());
            }

            if (totalTercapaiigadus > 0){
                double overallPercentagegadus = (totalTercapaiigadus * 100.0) / sales.getTargetgadus();
                sales.setTercapaipersengadus(roundToTwoDecimalPlaces(overallPercentagegadus));
            } else {
                double overallPercentagegadus = (salesDto.getTercapaigadus() * 100.0) / sales.getTargetgadus();
                sales.setTercapaipersengadus(roundToTwoDecimalPlaces(overallPercentagegadus));
            }


            double totalTercapaiipremium = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getTercapaiipremium)
                    .sum();

            if (totalTercapaiipremium > 0) {
                sales.setTercapaipremium((int) totalTercapaiipremium);
            } else {
                sales.setTercapaipremium(salesDto.getTercapaipremium());
            }

            if (totalTercapaiipremium > 0){
                double overallPercentagepremium = (totalTercapaiipremium * 100.0) / sales.getTargetpremium();
                sales.setTercapaipersenpremium(roundToTwoDecimalPlaces(overallPercentagepremium));
            } else {
                double overallPercentagepremium = (salesDto.getTercapaipremium() * 100.0) / sales.getTargetpremium();
                sales.setTercapaipersenpremium(roundToTwoDecimalPlaces(overallPercentagepremium));
            }

            double totalVisit = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getJumlahvisit)
                    .sum();

            if ( totalVisit > 0){
                int numberOfDetails = sales.getSalesDetails().size();
                double averageVisit = numberOfDetails > 0 ? totalVisit / numberOfDetails : 0;
                sales.setJumlahvisit(averageVisit);
            } else {
                sales.setJumlahvisit(salesDto.getJumlahvisit());
            }

            salesRepository.save(sales);

            List<SalesDetailDto> salesDetailDtoList = salesDto.getSalesDetailDtoList();
            if (salesDetailDtoList != null) {
                for (SalesDetailDto salesDetailDto : salesDetailDtoList) {
                    Optional<SalesDetail> optionalSalesDetail = salesDetailRepository.findById(salesDetailDto.getId());
                    if (optionalSalesDetail.isPresent()) {
                        // If SalesDetail with the given ID exists, update it
                        SalesDetail salesDetail = optionalSalesDetail.get();
                        salesDetail.setBulan(salesDetailDto.getBulan());
                        salesDetail.setTargetblntotal(salesDetailDto.getTargetblntotal());
                        salesDetail.setTercapaiitotal(salesDetailDto.getTercapaiitotal());
                        salesDetail.setTercapaipersenntotal(roundToTwoDecimalPlaces(salesDetailDto.getTercapaipersenntotal()));

                        salesDetail.setTargetblngadus(salesDetailDto.getTargetblngadus());
                        salesDetail.setTercapaiigadus(salesDetailDto.getTercapaiigadus());
                        salesDetail.setTercapaipersenngadus(roundToTwoDecimalPlaces(salesDetailDto.getTercapaipersenngadus()));

                        salesDetail.setTargetblnpremium(salesDetailDto.getTargetblnpremium());
                        salesDetail.setTercapaiipremium(salesDetailDto.getTercapaiipremium());
                        salesDetail.setTercapaipersennpremium(roundToTwoDecimalPlaces(salesDetailDto.getTercapaipersennpremium()));

                        salesDetail.setJumlahvisit(salesDetailDto.getJumlahvisit());

                        salesDetailRepository.save(salesDetail);
                    } else {
                        // If SalesDetail with the given ID doesn't exist, create new SalesDetail
                        SalesDetail salesDetail = new SalesDetail();
                        salesDetail.setBulan(salesDetailDto.getBulan());
                        salesDetail.setTargetblntotal(salesDetailDto.getTargetblntotal());
                        salesDetail.setTercapaiitotal(salesDetailDto.getTercapaiitotal());
                        salesDetail.setTercapaipersenntotal(roundToTwoDecimalPlaces(salesDetailDto.getTercapaipersenntotal()));

                        salesDetail.setTargetblngadus(salesDetailDto.getTargetblngadus());
                        salesDetail.setTercapaiigadus(salesDetailDto.getTercapaiigadus());
                        salesDetail.setTercapaipersenngadus(roundToTwoDecimalPlaces(salesDetailDto.getTercapaipersenngadus()));

                        salesDetail.setTargetblnpremium(salesDetailDto.getTargetblnpremium());
                        salesDetail.setTercapaiipremium(salesDetailDto.getTercapaiipremium());
                        salesDetail.setTercapaipersennpremium(roundToTwoDecimalPlaces(salesDetailDto.getTercapaipersennpremium()));

                        salesDetail.setJumlahvisit(salesDetailDto.getJumlahvisit());
                        salesDetail.setSales(sales);
                        salesDetailRepository.save(salesDetail);
                    }
                }
            } else {

            }

            return ResponseEntity.ok("Data Sales edited successfully");
        } catch (Exception e) {
            log.error("Error editing data sales", e);
            return ResponseEntity.status(500).body("Error editing data sales");
        }
    }

    public ResponseEntity<Object> deleteSales(Long id) {
        try {
            log.info("Inside delete sales");
            Optional<Sales> optionalSales = salesRepository.findById(id);

            if (optionalSales.isPresent()) {
                salesRepository.deleteById(id);
                return ResponseEntity.ok("Successfully deleted data sales");
            } else {
                return ResponseEntity.status(404).body("data sales not found");
            }
        } catch (Exception e) {
            log.error("Error delete data sales", e);
            return ResponseEntity.status(500).body("Error delete data sales");
        }
    }

    public Page<SalesDto> showAllAndPaginationSales(Integer tahun, String nama, String order, int offset, int pageSize) {
        log.info("Inside showAllAndPaginationSales");
        Page<Sales> salesPage;

        if (tahun != null && nama != null) {
            salesPage = salesRepository.findByTahunAndNamaContainingIgnoreCase(tahun, nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (tahun != null) {
            salesPage = salesRepository.findByTahun(tahun, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (nama != null) {
            salesPage = salesRepository.findByNamaContainingIgnoreCase(nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else {
            salesPage = salesRepository.findAll(PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        }

        List<SalesDto> resultList = salesPage.getContent().stream()
                .map(sales -> {
                    SalesDto salesDto = new SalesDto();
                    Karyawan karyawan = sales.getKaryawan();
                    salesDto.setIdsales(sales.getIdsales());
                    salesDto.setNik(karyawan.getNik());
                    salesDto.setNama(karyawan.getNama());
                    salesDto.setJabatan(karyawan.getJabatan());
                    salesDto.setTahun(sales.getTahun());
                    salesDto.setTargettotal(sales.getTargettotal());
                    salesDto.setTargetgadus(sales.getTargetgadus());
                    salesDto.setTargetpremium(sales.getTargetpremium());
                    salesDto.setTercapaitotal(sales.getTercapaitotal());
                    salesDto.setTercapaigadus(sales.getTercapaigadus());
                    salesDto.setTercapaipremium(sales.getTercapaipremium());
                    salesDto.setTercapaipersentotal(sales.getTercapaipersentotal());
                    salesDto.setTercapaipersengadus(sales.getTercapaipersengadus());
                    salesDto.setTercapaipersenpremium(sales.getTercapaipersenpremium());
                    salesDto.setJumlahcustomer(sales.getJumlahcustomer());
                    salesDto.setJumlahvisit(sales.getJumlahvisit());

                    // Map SalesDetail information
                    List<SalesDetailDto> salesDetailDtoList = sales.getSalesDetails().stream()
                            .map(saless -> {
                                SalesDetailDto salesDetailDto = new SalesDetailDto();
                                salesDetailDto.setId(saless.getId());
                                salesDetailDto.setBulan(saless.getBulan());

                                salesDetailDto.setTargetblntotal(saless.getTargetblntotal());
                                salesDetailDto.setTercapaiitotal(saless.getTercapaiitotal());
                                salesDetailDto.setTercapaipersenntotal(saless.getTercapaipersenntotal());

                                salesDetailDto.setTargetblngadus(saless.getTargetblngadus());
                                salesDetailDto.setTercapaiigadus(saless.getTercapaiigadus());
                                salesDetailDto.setTercapaipersenngadus(saless.getTercapaipersenngadus());

                                salesDetailDto.setTargetblnpremium(saless.getTargetblnpremium());
                                salesDetailDto.setTercapaiipremium(saless.getTercapaiipremium());
                                salesDetailDto.setTercapaipersennpremium(saless.getTercapaipersennpremium());

                                salesDetailDto.setJumlahvisit(saless.getJumlahvisit());

                                salesDetailDto.setIdsales(saless.getSales().getIdsales());
                                return salesDetailDto;
                            })
                            .collect(Collectors.toList());
                    salesDto.setSalesDetailDtoList(salesDetailDtoList);
                    return salesDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, salesPage.getPageable(), salesPage.getTotalElements());
    }

    public Page<AchivementTotalDto> achivementTotalPagination(Integer tahun, String nama, String order, int offset, int pageSize) {
        log.info("Inside showachivementTotalPagination");
        Page<Sales> salesPage;

        if (tahun != null && nama != null) {
            salesPage = salesRepository.findByTahunAndNamaContainingIgnoreCase(tahun, nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (tahun != null) {
            salesPage = salesRepository.findByTahun(tahun, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (nama != null) {
            salesPage = salesRepository.findByNamaContainingIgnoreCase(nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else {
            salesPage = salesRepository.findAll(PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        }
        List<AchivementTotalDto> resultList = salesPage.getContent().stream()
                .map(sales -> {
                    AchivementTotalDto achivementTotalDto = new AchivementTotalDto();
                    Karyawan karyawan = sales.getKaryawan();
                    achivementTotalDto.setIdsales(sales.getIdsales());
                    achivementTotalDto.setNik(karyawan.getNik());
                    achivementTotalDto.setNama(karyawan.getNama());
                    achivementTotalDto.setTahun(sales.getTahun());
                    achivementTotalDto.setTargettotal(sales.getTargettotal());
                    achivementTotalDto.setTercapaitotal(sales.getTercapaitotal());
                    achivementTotalDto.setTercapaipersentotal(sales.getTercapaipersentotal());

                    return achivementTotalDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, salesPage.getPageable(), salesPage.getTotalElements());

    }

    public Page<AchivementGadusDto> achivementGadusPagination(Integer tahun, String nama, String order, int offset, int pageSize) {
        log.info("Inside showachivementGadusPagination");
        Page<Sales> salesPage;

        if (tahun != null && nama != null) {
            salesPage = salesRepository.findByTahunAndNamaContainingIgnoreCase(tahun, nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (tahun != null) {
            salesPage = salesRepository.findByTahun(tahun, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (nama != null) {
            salesPage = salesRepository.findByNamaContainingIgnoreCase(nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else {
            salesPage = salesRepository.findAll(PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        }
        List<AchivementGadusDto> resultList = salesPage.getContent().stream()
                .map(sales -> {
                    AchivementGadusDto achivementGadusDto = new AchivementGadusDto();
                    Karyawan karyawan = sales.getKaryawan();
                    achivementGadusDto.setIdsales(sales.getIdsales());
                    achivementGadusDto.setNik(karyawan.getNik());
                    achivementGadusDto.setNama(karyawan.getNama());
                    achivementGadusDto.setTahun(sales.getTahun());
                    achivementGadusDto.setTargetgadus(sales.getTargetgadus());
                    achivementGadusDto.setTercapaigadus(sales.getTercapaigadus());
                    achivementGadusDto.setTercapaipersengadus(sales.getTercapaipersengadus());
                    return achivementGadusDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, salesPage.getPageable(), salesPage.getTotalElements());

    }

    public Page<AchivementPremiumDto> achivementPremiumPagination(Integer tahun, String nama, String order, int offset, int pageSize) {
        log.info("Inside showachivementPremiumPagination");
        Page<Sales> salesPage;

        if (tahun != null && nama != null) {
            salesPage = salesRepository.findByTahunAndNamaContainingIgnoreCase(tahun, nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (tahun != null) {
            salesPage = salesRepository.findByTahun(tahun, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (nama != null) {
            salesPage = salesRepository.findByNamaContainingIgnoreCase(nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else {
            salesPage = salesRepository.findAll(PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        }
        List<AchivementPremiumDto> resultList = salesPage.getContent().stream()
                .map(sales -> {
                    AchivementPremiumDto achivementPremiumDto = new AchivementPremiumDto();
                    Karyawan karyawan = sales.getKaryawan();
                    achivementPremiumDto.setIdsales(sales.getIdsales());
                    achivementPremiumDto.setNik(karyawan.getNik());
                    achivementPremiumDto.setNama(karyawan.getNama());
                    achivementPremiumDto.setTahun(sales.getTahun());
                    achivementPremiumDto.setTargetpremium(sales.getTargetpremium());
                    achivementPremiumDto.setTercapaipremium(sales.getTercapaipremium());
                    achivementPremiumDto.setTercapaipersenpremium(sales.getTercapaipersenpremium());
                    return achivementPremiumDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, salesPage.getPageable(), salesPage.getTotalElements());


    }

    public Page<JumlahCustomerAndVisitDto> jumlahCustomerAndVisitPagination(Integer tahun, String nama, String order, int offset, int pageSize) {
        log.info("Inside jumlahCustomerAndVisitPagination");
        Page<Sales> salesPage;

        if (tahun != null && nama != null) {
            salesPage = salesRepository.findByTahunAndNamaContainingIgnoreCase(tahun, nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (tahun != null) {
            salesPage = salesRepository.findByTahun(tahun, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (nama != null) {
            salesPage = salesRepository.findByNamaContainingIgnoreCase(nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else {
            salesPage = salesRepository.findAll(PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        }
        List<JumlahCustomerAndVisitDto> resultList = salesPage.getContent().stream()
                .map(sales -> {
                    JumlahCustomerAndVisitDto jumlahCustomerAndVisitDto = new JumlahCustomerAndVisitDto();
                    Karyawan karyawan = sales.getKaryawan();
                    jumlahCustomerAndVisitDto.setIdsales(sales.getIdsales());
                    jumlahCustomerAndVisitDto.setNik(karyawan.getNik());
                    jumlahCustomerAndVisitDto.setNama(karyawan.getNama());
                    jumlahCustomerAndVisitDto.setTahun(sales.getTahun());
                    jumlahCustomerAndVisitDto.setJumlahcustomer(sales.getJumlahcustomer());
                    jumlahCustomerAndVisitDto.setJumlahvisit(sales.getJumlahvisit());
                    return jumlahCustomerAndVisitDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, salesPage.getPageable(), salesPage.getTotalElements());

    }


    public SalesDto fetchSalesDtoByIdsales(Long id) throws AllException {
        log.info("Inside fetchUserDtoByIduser");
        Sales sales = salesRepository.findById(id)
                .orElseThrow(() -> new AllException("Sales with idsales " + id + " not found"));

        SalesDto salesDto = new SalesDto();
        Karyawan karyawan = sales.getKaryawan();
        salesDto.setIdsales(sales.getIdsales());
        salesDto.setNik(karyawan.getNik());
        salesDto.setNama(karyawan.getNama());
        salesDto.setJabatan(karyawan.getJabatan());
        salesDto.setTahun(sales.getTahun());
        salesDto.setTargettotal(sales.getTargettotal());
        salesDto.setTargetgadus(sales.getTargetgadus());
        salesDto.setTargetpremium(sales.getTargetpremium());
        salesDto.setTercapaitotal(sales.getTercapaitotal());
        salesDto.setTercapaigadus(sales.getTercapaigadus());
        salesDto.setTercapaipremium(sales.getTercapaipremium());
        salesDto.setTercapaipersentotal(sales.getTercapaipersentotal());
        salesDto.setTercapaipersengadus(sales.getTercapaipersengadus());
        salesDto.setTercapaipersenpremium(sales.getTercapaipersenpremium());
        salesDto.setJumlahcustomer(sales.getJumlahcustomer());
        salesDto.setJumlahvisit(sales.getJumlahvisit());

        List<SalesDetailDto> salesDetailDtoList = sales.getSalesDetails().stream()
                .map(saless -> {
                    SalesDetailDto salesDetailDto = new SalesDetailDto();
                    salesDetailDto.setId(saless.getId());
                    salesDetailDto.setBulan(saless.getBulan());

                    salesDetailDto.setTargetblntotal(saless.getTargetblntotal());
                    salesDetailDto.setTercapaiitotal(saless.getTercapaiitotal());
                    salesDetailDto.setTercapaipersenntotal(saless.getTercapaipersenntotal());

                    salesDetailDto.setTargetblngadus(saless.getTargetblngadus());
                    salesDetailDto.setTercapaiigadus(saless.getTercapaiigadus());
                    salesDetailDto.setTercapaipersenngadus(saless.getTercapaipersenngadus());

                    salesDetailDto.setTargetblnpremium(saless.getTargetblnpremium());
                    salesDetailDto.setTercapaiipremium(saless.getTercapaiipremium());
                    salesDetailDto.setTercapaipersennpremium(saless.getTercapaipersennpremium());

                    salesDetailDto.setJumlahvisit(saless.getJumlahvisit());

                    salesDetailDto.setIdsales(saless.getSales().getIdsales());
                    return salesDetailDto;
                })
                .collect(Collectors.toList());
        salesDto.setSalesDetailDtoList(salesDetailDtoList);

        return salesDto;
    }

    public Page<PenilaianSalesDto> paginationPenilaianSales(Integer tahun,String nama, String order, int offset, int pageSize) {
        log.info("Inside Pagination Penilaian Sales");
        Page<Sales> salesPage;
        if (tahun != null && nama != null) {
            salesPage = salesRepository.findByTahunAndNamaContainingIgnoreCase(tahun, nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (tahun != null) {
            salesPage = salesRepository.findByTahun(tahun, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (nama != null) {
            salesPage = salesRepository.findByNamaContainingIgnoreCase(nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else {
            salesPage = salesRepository.findAll(PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        }

        List<PenilaianSalesDto> resultList = salesPage.getContent().stream()
                .map(sales -> {
                    PenilaianSalesDto penilaianSalesDto = new PenilaianSalesDto();
                    Karyawan karyawan = sales.getKaryawan();
                    penilaianSalesDto.setIdsales(sales.getIdsales());
                    penilaianSalesDto.setNama(karyawan.getNama());
                    penilaianSalesDto.setTahun(sales.getTahun());

                            penilaianSalesDto.setAchievtotal(sales.getTercapaipersentotal());

                            penilaianSalesDto.setAchievgadus(sales.getTercapaipersengadus());

                            penilaianSalesDto.setAchievpremium(sales.getTercapaipersenpremium());

                            penilaianSalesDto.setJumvisit(sales.getJumlahvisit());

                            penilaianSalesDto.setJumcustomer(sales.getJumlahcustomer());

                    return penilaianSalesDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, salesPage.getPageable(), salesPage.getTotalElements());

    }

    public Page<PenilaianSalesDto> paginationPenilaianKriteria(Integer tahun,String nama, String order, int offset, int pageSize) {
        log.info("Inside pagination Penilaian Kriteria");
        Page<Sales> salesPage;
        if (tahun != null && nama != null) {
            salesPage = salesRepository.findByTahunAndNamaContainingIgnoreCase(tahun, nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (tahun != null) {
            salesPage = salesRepository.findByTahun(tahun, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (nama != null) {
            salesPage = salesRepository.findByNamaContainingIgnoreCase(nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else {
            salesPage = salesRepository.findAll(PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        }
        List<PenilaianSalesDto> resultList = salesPage.getContent().stream()
                .map(sales -> {
                    PenilaianSalesDto penilaianSalesDto = new PenilaianSalesDto();
                    Karyawan karyawan = sales.getKaryawan();
                    penilaianSalesDto.setIdsales(sales.getIdsales());
                    penilaianSalesDto.setNama(karyawan.getNama());
                    penilaianSalesDto.setTahun(sales.getTahun());

                    penilaianSalesDto.setAchievtotal(getNilaiForAchivement("Achivement Total", sales.getTercapaipersentotal()));
                    penilaianSalesDto.setAchievgadus(getNilaiForAchivement("Achivement Gadus", sales.getTercapaipersengadus()));
                    penilaianSalesDto.setAchievpremium(getNilaiForAchivement("Achivement Premium", sales.getTercapaipersenpremium()));
                    penilaianSalesDto.setJumvisit(getNilaiForAchivement("Jumlah Visit", sales.getJumlahvisit()));
                    penilaianSalesDto.setJumcustomer(getNilaiForAchivement("Jumlah Customer", sales.getJumlahcustomer()));

                    return penilaianSalesDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, salesPage.getPageable(), salesPage.getTotalElements());
    }

    public Page<PenilaianSalesDto> normalisasiMatrikskeputusan(Integer tahun,String nama, String order, int offset, int pageSize) {
        log.info("Inside normalisasi matriks keputusan");

        Page<Sales> salesPage;
        if (tahun != null && nama != null) {
            salesPage = salesRepository.findByTahunAndNamaContainingIgnoreCase(tahun, nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (tahun != null) {
            salesPage = salesRepository.findByTahun(tahun, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (nama != null) {
            salesPage = salesRepository.findByNamaContainingIgnoreCase(nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else {
            salesPage = salesRepository.findAll(PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        }

        List<Sales> salesList = salesPage.getContent();

        // Calculate max nilai for each achievement type
        double maxNilaiAchivementTotal = getMaxNilai("Achivement Total");
        double maxNilaiAchivementGadus = getMaxNilai("Achivement Gadus");
        double maxNilaiAchivementPremium = getMaxNilai("Achivement Premium");
        double maxNilaiJumlahVisit = getMaxNilai("Jumlah Visit");
        double maxNilaiJumlahCustomer = getMaxNilai("Jumlah Customer");

        List<PenilaianSalesDto> resultList = salesList.stream()
                .map(sales -> {
                    PenilaianSalesDto penilaianSalesDto = new PenilaianSalesDto();
                    Karyawan karyawan = sales.getKaryawan();
                    penilaianSalesDto.setIdsales(sales.getIdsales());
                    penilaianSalesDto.setNama(karyawan.getNama());
                    penilaianSalesDto.setTahun(sales.getTahun());

                    // Normalize and set values
                    penilaianSalesDto.setAchievtotal(getNilaiForAchivement("Achivement Total", sales.getTercapaipersentotal()) / maxNilaiAchivementTotal);
                    penilaianSalesDto.setAchievgadus(getNilaiForAchivement("Achivement Gadus", sales.getTercapaipersengadus()) / maxNilaiAchivementGadus);
                    penilaianSalesDto.setAchievpremium(getNilaiForAchivement("Achivement Premium", sales.getTercapaipersenpremium()) / maxNilaiAchivementPremium);
                    penilaianSalesDto.setJumvisit(getNilaiForAchivement("Jumlah Visit", sales.getJumlahvisit()) / maxNilaiJumlahVisit);
                    penilaianSalesDto.setJumcustomer(getNilaiForAchivement("Jumlah Customer", sales.getJumlahcustomer()) / maxNilaiJumlahCustomer);

                    return penilaianSalesDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, salesPage.getPageable(), salesPage.getTotalElements());
    }

    public Page<RankDto> perangkinganSales(Integer tahun,String nama, String order, int offset, int pageSize) {
        log.info("Inside perangkinganSales");

        Page<Sales> salesPage;
        if (tahun != null && nama != null) {
            salesPage = salesRepository.findByTahunAndNamaContainingIgnoreCase(tahun, nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (tahun != null) {
            salesPage = salesRepository.findByTahun(tahun, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (nama != null) {
            salesPage = salesRepository.findByNamaContainingIgnoreCase(nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else {
            salesPage = salesRepository.findAll(PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        }

        List<Sales> salesList = salesPage.getContent();

        // Calculate max nilai for each achievement type
        double maxNilaiAchivementTotal = getMaxNilai("Achivement Total");
        double maxNilaiAchivementGadus = getMaxNilai("Achivement Gadus");
        double maxNilaiAchivementPremium = getMaxNilai("Achivement Premium");
        double maxNilaiJumlahVisit = getMaxNilai("Jumlah Visit");
        double maxNilaiJumlahCustomer = getMaxNilai("Jumlah Customer");

        List<RankDto> resultList = salesList.stream()
                .map(sales -> {
                    RankDto rankDto = new RankDto();
                    Karyawan karyawan = sales.getKaryawan();
                    rankDto.setIdsales(sales.getIdsales());
                    rankDto.setNama(karyawan.getNama());
                    rankDto.setTahun(sales.getTahun());

                    // Calculate normalized and weighted values
                    double achivementTotal = getNilaiForAchivement("Achivement Total", sales.getTercapaipersentotal()) / maxNilaiAchivementTotal;
                    double achivementGadus = getNilaiForAchivement("Achivement Gadus", sales.getTercapaipersengadus()) / maxNilaiAchivementGadus;
                    double achivementPremium = getNilaiForAchivement("Achivement Premium", sales.getTercapaipersenpremium()) / maxNilaiAchivementPremium;
                    double jumlahVisit = getNilaiForAchivement("Jumlah Visit", sales.getJumlahvisit()) / maxNilaiJumlahVisit;
                    double jumlahCustomer = getNilaiForAchivement("Jumlah Customer", sales.getJumlahcustomer()) / maxNilaiJumlahCustomer;

                    // Apply weights
                    achivementTotal *= getBobotForKriteria("Achivement Total");
                    achivementGadus *= getBobotForKriteria("Achivement Gadus");
                    achivementPremium *= getBobotForKriteria("Achivement Premium");
                    jumlahVisit *= getBobotForKriteria("Jumlah Visit");
                    jumlahCustomer *= getBobotForKriteria("Jumlah Customer");

                    // Set values to rankDto
                    rankDto.setAchivementtotal(achivementTotal);
                    rankDto.setAchivementgadus(achivementGadus);
                    rankDto.setAchivementpremium(achivementPremium);
                    rankDto.setJumvisit(jumlahVisit);
                    rankDto.setJumcustomer(jumlahCustomer);

                    // Calculate hasil (total score) and set rank
                    rankDto.setHasil(achivementTotal + achivementGadus + achivementPremium + jumlahVisit + jumlahCustomer);

                    return rankDto;
                })
                .collect(Collectors.toList());

        // Sort resultList by hasil and assign ranks
        resultList.sort((r1, r2) -> Double.compare(r2.getHasil(), r1.getHasil()));
        for (int i = 0; i < resultList.size(); i++) {
            resultList.get(i).setRank(i + 1);
        }

        return new PageImpl<>(resultList, salesPage.getPageable(), salesPage.getTotalElements());
    }

    private int getBobotForKriteria(String nmkriteria) {
        List<BobotKriteria> bobotKriteriaList = bobotKriteriaRepository.findByNmkriteria(nmkriteria);
        for (BobotKriteria bobotKriteria : bobotKriteriaList) {
            return bobotKriteria.getBobot();
        }
        return 0;
    }

    private double getMaxNilai(String nmkriteria) {
        List<HimpunanKriteria> himpunanKriteriaList = himpunanKriteriaRepository.findByNmkriteria(nmkriteria);
        return himpunanKriteriaList.stream()
                .mapToInt(HimpunanKriteria::getNilai)
                .max()
                .orElse(1); // default to 1 if no values found
    }

    private int getNilaiForAchivement(String nmkriteria, double achivement) {
        if (achivement >= 200) {
            return 7;
        }
        List<HimpunanKriteria> himpunanKriteriaList = himpunanKriteriaRepository.findByNmkriteria(nmkriteria);
        for (HimpunanKriteria himpunanKriteria : himpunanKriteriaList) {
            String nmhimpunan = himpunanKriteria.getNmhimpunan();
            int nilai = himpunanKriteria.getNilai();
            if (isAchivementInRange(nmhimpunan, Math.round(achivement))) {
                return nilai;
            }
        }
        return 0;
    }
    
private boolean isAchivementInRange(String nmhimpunan, double achivement) {
    String[] range = nmhimpunan.split("-");
    if (range.length == 2) {
        int lowerBound = Integer.parseInt(range[0]);
        int upperBound = Integer.parseInt(range[1]);
        return achivement >= lowerBound && (achivement <= upperBound || (upperBound == 100 && achivement > 100));
    }
    return false;
}

    public Page<PenilaianSalesBulananDto> paginationPenilaianSalesBulanan(Integer tahun, String nama, String bulan, String order, int offset, int pageSize) {
        log.info("Inside Pagination Penilaian Sales Bulanan");
        Page<Sales> salesPage;
        if (tahun != null && nama != null && bulan != null) {
            salesPage = salesRepository.findByTahunAndNamaAndBulanContainingIgnoreCase(tahun, nama,bulan, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (tahun != null && nama != null) {
            salesPage = salesRepository.findByTahunAndNamaContainingIgnoreCase(tahun,nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (tahun != null && bulan != null) {
            salesPage = salesRepository.findByTahunAndBulanContainingIgnoreCase(tahun,bulan, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (nama != null && bulan != null) {
            salesPage = salesRepository.findByNamaAndBulanContainingIgnoreCase(nama,bulan, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (tahun != null) {
            salesPage = salesRepository.findByTahun(tahun, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (bulan != null) {
            salesPage = salesRepository.findByBulanContainingIgnoreCase(bulan, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        }else if (nama != null) {
            salesPage = salesRepository.findByNamaContainingIgnoreCase(nama, PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else {
            salesPage = salesRepository.findAll(PageRequest.of(offset - 1, pageSize,
                    "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        }

        List<PenilaianSalesBulananDto> resultList = salesPage.getContent().stream()
                .flatMap(sales -> sales.getSalesDetails().stream()
                        .map(salesDetail -> {
                            PenilaianSalesBulananDto penilaianSalesBulananDto = new PenilaianSalesBulananDto();
                            Karyawan karyawan = sales.getKaryawan();

                            penilaianSalesBulananDto.setIdsales(sales.getIdsales());
                            penilaianSalesBulananDto.setNama(karyawan.getNama());
                            penilaianSalesBulananDto.setTahun(sales.getTahun());
                            penilaianSalesBulananDto.setBulan(salesDetail.getBulan());
                            penilaianSalesBulananDto.setId(salesDetail.getId());

                            penilaianSalesBulananDto.setAchievtotal(salesDetail.getTercapaipersenntotal());
                            penilaianSalesBulananDto.setAchievgadus(salesDetail.getTercapaipersenngadus());
                            penilaianSalesBulananDto.setAchievpremium(salesDetail.getTercapaipersennpremium());
                            penilaianSalesBulananDto.setJumvisit(salesDetail.getJumlahvisit());
                            penilaianSalesBulananDto.setJumcustomer(sales.getJumlahcustomer());

                            return penilaianSalesBulananDto;
                        }))
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, salesPage.getPageable(), salesPage.getTotalElements());

    }
}
