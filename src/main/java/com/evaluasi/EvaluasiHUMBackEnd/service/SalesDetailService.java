package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.PenilaianSalesBulananDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.PenilaianSalesDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.RankBulanDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.SalesDetailDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.*;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.repository.BobotKriteriaRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.HimpunanKriteriaRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.SalesDetailRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SalesDetailService {
    private final SalesDetailRepository salesDetailRepository;
    private final SalesRepository salesRepository;
    private final HimpunanKriteriaRepository himpunanKriteriaRepository;
    private final BobotKriteriaRepository bobotKriteriaRepository;

    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

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
            salesDetail.setTercapaipersenntotal(roundToTwoDecimalPlaces(percenttotal));

            salesDetail.setTargetblngadus(salesDetailDto.getTargetblngadus());
            salesDetail.setTercapaiigadus(salesDetailDto.getTercapaiigadus());
            double percentgadus = (salesDetail.getTercapaiigadus() * 100.0) / salesDetail.getTargetblngadus();
            salesDetail.setTercapaipersenngadus(roundToTwoDecimalPlaces(percentgadus));

            salesDetail.setTargetblnpremium(salesDetailDto.getTargetblnpremium());
            salesDetail.setTercapaiipremium(salesDetailDto.getTercapaiipremium());
            double percentpremium = (salesDetail.getTercapaiipremium() * 100.0) / salesDetail.getTargetblnpremium();
            salesDetail.setTercapaipersennpremium(roundToTwoDecimalPlaces(percentpremium));

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

            sales.setTercapaipersentotal(roundToTwoDecimalPlaces(overallPercentagetotal));



            double totalTercapaiigadus = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getTercapaiigadus)
                    .sum();

            sales.setTercapaigadus((int) totalTercapaiigadus);

            double overallPercentagegadus = (totalTercapaiigadus * 100.0) / (sales.getTargetgadus());

            sales.setTercapaipersengadus(roundToTwoDecimalPlaces(overallPercentagegadus));


            double totalTercapaiipremium = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getTercapaiipremium)
                    .sum();

            sales.setTercapaipremium((int) totalTercapaiipremium);

            double overallPercentagepremium = (totalTercapaiipremium * 100.0) / (sales.getTargetpremium());

            sales.setTercapaipersenpremium(roundToTwoDecimalPlaces(overallPercentagepremium));


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
            salesDetail.setTercapaipersenntotal(roundToTwoDecimalPlaces(percenttotal));

            salesDetail.setTargetblngadus(salesDetailDto.getTargetblngadus());
            salesDetail.setTercapaiigadus(salesDetailDto.getTercapaiigadus());
            double percentgadus = (salesDetail.getTercapaiigadus() * 100.0) / salesDetail.getTargetblngadus();
            salesDetail.setTercapaipersenngadus(roundToTwoDecimalPlaces(percentgadus));

            salesDetail.setTargetblnpremium(salesDetailDto.getTargetblnpremium());
            salesDetail.setTercapaiipremium(salesDetailDto.getTercapaiipremium());
            double percentpremium = (salesDetail.getTercapaiipremium() * 100.0) / salesDetail.getTargetblnpremium();
            salesDetail.setTercapaipersennpremium(roundToTwoDecimalPlaces(percentpremium));

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

            sales.setTercapaipersentotal(roundToTwoDecimalPlaces(overallPercentagetotal));



            double totalTercapaiigadus = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getTercapaiigadus)
                    .sum();

            sales.setTercapaigadus((int) totalTercapaiigadus);

            double overallPercentagegadus = (totalTercapaiigadus * 100.0) / (sales.getTargetgadus());

            sales.setTercapaipersengadus(roundToTwoDecimalPlaces(overallPercentagegadus));


            double totalTercapaiipremium = sales.getSalesDetails().stream()
                    .mapToDouble(SalesDetail::getTercapaiipremium)
                    .sum();

            sales.setTercapaipremium((int) totalTercapaiipremium);

            double overallPercentagepremium = (totalTercapaiipremium * 100.0) / (sales.getTargetpremium());

            sales.setTercapaipersenpremium(roundToTwoDecimalPlaces(overallPercentagepremium));


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

                sales.setTercapaipersentotal(roundToTwoDecimalPlaces(overallPercentagetotal));



                double totalTercapaiigadus = sales.getSalesDetails().stream()
                        .mapToDouble(SalesDetail::getTercapaiigadus)
                        .sum();

                sales.setTercapaigadus((int) totalTercapaiigadus);

                double overallPercentagegadus = (totalTercapaiigadus * 100.0) / (sales.getTargetgadus());

                sales.setTercapaipersengadus(roundToTwoDecimalPlaces(overallPercentagegadus));


                double totalTercapaiipremium = sales.getSalesDetails().stream()
                        .mapToDouble(SalesDetail::getTercapaiipremium)
                        .sum();

                sales.setTercapaipremium((int) totalTercapaiipremium);

                double overallPercentagepremium = (totalTercapaiipremium * 100.0) / (sales.getTargetpremium());

                sales.setTercapaipersenpremium(roundToTwoDecimalPlaces(overallPercentagepremium));


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

    public Page<PenilaianSalesBulananDto> paginationPenilaianSalesBulanan(Integer tahun, String nama, String bulan, String order, int offset, int pageSize) {
        log.info("Inside Pagination Penilaian Kriteria Sales Bulanan");
        Sort sort = "desc".equals(order) ? Sort.by("id").descending() : Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(offset - 1, pageSize, sort);
        Page<SalesDetail> salesDetailPage;

        // Fetch the SalesDetail page based on provided filters
        if (tahun != null && nama != null && bulan != null) {
            salesDetailPage = salesDetailRepository.findByTahunAndNamaAndBulanContainingIgnoreCase(tahun, nama, bulan, pageable);
        } else if (tahun != null && nama != null) {
            salesDetailPage = salesDetailRepository.findByTahunAndNamaContainingIgnoreCase(tahun, nama, pageable);
        } else if (tahun != null && bulan != null) {
            salesDetailPage = salesDetailRepository.findByTahunAndBulan(tahun, bulan, pageable);
        } else if (nama != null && bulan != null) {
            salesDetailPage = salesDetailRepository.findByNamaAndBulanContainingIgnoreCase(nama, bulan, pageable);
        } else if (tahun != null) {
            salesDetailPage = salesDetailRepository.findByTahun(tahun, pageable);
        } else if (bulan != null) {
            salesDetailPage = salesDetailRepository.findByBulan(bulan, pageable);
        } else if (nama != null) {
            salesDetailPage = salesDetailRepository.findByNamaContainingIgnoreCase(nama, pageable);
        } else {
            salesDetailPage = salesDetailRepository.findAll(pageable);
        }

        List<PenilaianSalesBulananDto> resultList = new ArrayList<>();
        // Loop through the SalesDetails and map them to PenilaianSalesBulananDto
        for (SalesDetail salesDetail : salesDetailPage.getContent()) {
            Sales sales = salesDetail.getSales();
            Karyawan karyawan = sales.getKaryawan();

            PenilaianSalesBulananDto penilaianSalesBulananDto = new PenilaianSalesBulananDto();
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
            // Add to resultList
            resultList.add(penilaianSalesBulananDto);
        }

        // Return as a paginated result
        return new PageImpl<>(resultList, pageable, salesDetailPage.getTotalElements());

    }

    public Page<PenilaianSalesBulananDto> paginationPenilaianKriteriaBulanan(Integer tahun, String nama, String bulan, String order, int offset, int pageSize) {
        log.info("Inside Pagination Penilaian Kriteria Sales Bulanan");
        Sort sort = "desc".equals(order) ? Sort.by("id").descending() : Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(offset - 1, pageSize, sort);
        Page<SalesDetail> salesDetailPage;

        // Fetch the SalesDetail page based on provided filters
        if (tahun != null && nama != null && bulan != null) {
            salesDetailPage = salesDetailRepository.findByTahunAndNamaAndBulanContainingIgnoreCase(tahun, nama, bulan, pageable);
        } else if (tahun != null && nama != null) {
            salesDetailPage = salesDetailRepository.findByTahunAndNamaContainingIgnoreCase(tahun, nama, pageable);
        } else if (tahun != null && bulan != null) {
            salesDetailPage = salesDetailRepository.findByTahunAndBulan(tahun, bulan, pageable);
        } else if (nama != null && bulan != null) {
            salesDetailPage = salesDetailRepository.findByNamaAndBulanContainingIgnoreCase(nama, bulan, pageable);
        } else if (tahun != null) {
            salesDetailPage = salesDetailRepository.findByTahun(tahun, pageable);
        } else if (bulan != null) {
            salesDetailPage = salesDetailRepository.findByBulan(bulan, pageable);
        } else if (nama != null) {
            salesDetailPage = salesDetailRepository.findByNamaContainingIgnoreCase(nama, pageable);
        } else {
            salesDetailPage = salesDetailRepository.findAll(pageable);
        }

        List<PenilaianSalesBulananDto> resultList = new ArrayList<>();

        // Loop through the SalesDetails and map them to PenilaianSalesBulananDto
        for (SalesDetail salesDetail : salesDetailPage.getContent()) {
            Sales sales = salesDetail.getSales();
            Karyawan karyawan = sales.getKaryawan();

            PenilaianSalesBulananDto penilaianSalesBulananDto = new PenilaianSalesBulananDto();
            penilaianSalesBulananDto.setIdsales(sales.getIdsales());
            penilaianSalesBulananDto.setNama(karyawan.getNama());
            penilaianSalesBulananDto.setTahun(sales.getTahun());
            penilaianSalesBulananDto.setBulan(salesDetail.getBulan());
            penilaianSalesBulananDto.setId(salesDetail.getId());

            penilaianSalesBulananDto.setAchievtotal(getNilaiForAchivement("Achivement Total",salesDetail.getTercapaipersenntotal()));
            penilaianSalesBulananDto.setAchievgadus(getNilaiForAchivement("Achivement Gadus",salesDetail.getTercapaipersenngadus()));
            penilaianSalesBulananDto.setAchievpremium(getNilaiForAchivement("Achivement Premium",salesDetail.getTercapaipersennpremium()));
            penilaianSalesBulananDto.setJumvisit(getNilaiForAchivement("Jumlah Visit",salesDetail.getJumlahvisit()));
            penilaianSalesBulananDto.setJumcustomer(getNilaiForAchivement("Jumlah Customer",sales.getJumlahcustomer()));

            // Add to resultList
            resultList.add(penilaianSalesBulananDto);
        }

        // Return as a paginated result
        return new PageImpl<>(resultList, pageable, salesDetailPage.getTotalElements());

    }

    public Page<PenilaianSalesBulananDto> normalisasiMatrikskeputusanBulanan(Integer tahun, String nama, String bulan, String order, int offset, int pageSize) {
        log.info("Inside Normalisasi Matriks Keputusan Bulanan");
        Sort sort = "desc".equals(order) ? Sort.by("id").descending() : Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(offset - 1, pageSize, sort);
        Page<SalesDetail> salesDetailPage;

        // Fetch the SalesDetail page based on provided filters
        if (tahun != null && nama != null && bulan != null) {
            salesDetailPage = salesDetailRepository.findByTahunAndNamaAndBulanContainingIgnoreCase(tahun, nama, bulan, pageable);
        } else if (tahun != null && nama != null) {
            salesDetailPage = salesDetailRepository.findByTahunAndNamaContainingIgnoreCase(tahun, nama, pageable);
        } else if (tahun != null && bulan != null) {
            salesDetailPage = salesDetailRepository.findByTahunAndBulan(tahun, bulan, pageable);
        } else if (nama != null && bulan != null) {
            salesDetailPage = salesDetailRepository.findByNamaAndBulanContainingIgnoreCase(nama, bulan, pageable);
        } else if (tahun != null) {
            salesDetailPage = salesDetailRepository.findByTahun(tahun, pageable);
        } else if (bulan != null) {
            salesDetailPage = salesDetailRepository.findByBulan(bulan, pageable);
        } else if (nama != null) {
            salesDetailPage = salesDetailRepository.findByNamaContainingIgnoreCase(nama, pageable);
        } else {
            salesDetailPage = salesDetailRepository.findAll(pageable);
        }

        // Calculate max values for normalizing scores
        double maxNilaiAchivementTotal = getMaxNilai("Achivement Total");
        double maxNilaiAchivementGadus = getMaxNilai("Achivement Gadus");
        double maxNilaiAchivementPremium = getMaxNilai("Achivement Premium");
        double maxNilaiJumlahVisit = getMaxNilai("Jumlah Visit");
        double maxNilaiJumlahCustomer = getMaxNilai("Jumlah Customer");

        List<PenilaianSalesBulananDto> resultList = new ArrayList<>();

        // Loop through the SalesDetails and map them to PenilaianSalesBulananDto
        for (SalesDetail salesDetail : salesDetailPage.getContent()) {
            Sales sales = salesDetail.getSales();
            Karyawan karyawan = sales.getKaryawan();

            PenilaianSalesBulananDto penilaianSalesBulananDto = new PenilaianSalesBulananDto();
            penilaianSalesBulananDto.setIdsales(sales.getIdsales());
            penilaianSalesBulananDto.setNama(karyawan.getNama());
            penilaianSalesBulananDto.setTahun(sales.getTahun());
            penilaianSalesBulananDto.setBulan(salesDetail.getBulan());
            penilaianSalesBulananDto.setId(salesDetail.getId());

            // Calculate normalized values and round to two decimal places
            double achievTotal = roundToTwoDecimalPlaces(getNilaiForAchivement("Achivement Total", salesDetail.getTercapaipersenntotal()) / maxNilaiAchivementTotal);
            double achievGadus = roundToTwoDecimalPlaces(getNilaiForAchivement("Achivement Gadus", salesDetail.getTercapaipersenngadus()) / maxNilaiAchivementGadus);
            double achievPremium = roundToTwoDecimalPlaces(getNilaiForAchivement("Achivement Premium", salesDetail.getTercapaipersennpremium()) / maxNilaiAchivementPremium);
            double jumVisit = roundToTwoDecimalPlaces(getNilaiForAchivement("Jumlah Visit", salesDetail.getJumlahvisit()) / maxNilaiJumlahVisit);
            double jumCustomer = roundToTwoDecimalPlaces(getNilaiForAchivement("Jumlah Customer", sales.getJumlahcustomer()) / maxNilaiJumlahCustomer);

            // Set the rounded values to the DTO
            penilaianSalesBulananDto.setAchievtotal(achievTotal);
            penilaianSalesBulananDto.setAchievgadus(achievGadus);
            penilaianSalesBulananDto.setAchievpremium(achievPremium);
            penilaianSalesBulananDto.setJumvisit(jumVisit);
            penilaianSalesBulananDto.setJumcustomer(jumCustomer);

            // Add to resultList
            resultList.add(penilaianSalesBulananDto);
        }

        // Return as a paginated result
        return new PageImpl<>(resultList, pageable, salesDetailPage.getTotalElements());
    }


    public Page<RankBulanDto> perangkinganSalesBulanan(Integer tahun, String nama, String bulan, String order, int offset, int pageSize) {
        log.info("Inside Perankingan sales bulanan");
        Page<SalesDetail> salesDetailPage;
        Sort sort = "desc".equals(order) ? Sort.by("id").descending() : Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(offset - 1, pageSize, sort);

        // Fetch the SalesDetail page based on provided filters
        if (tahun != null && nama != null && bulan != null) {
            salesDetailPage = salesDetailRepository.findByTahunAndNamaAndBulanContainingIgnoreCase(tahun, nama, bulan, pageable);
        } else if (tahun != null && nama != null) {
            salesDetailPage = salesDetailRepository.findByTahunAndNamaContainingIgnoreCase(tahun, nama, pageable);
        } else if (tahun != null && bulan != null) {
            salesDetailPage = salesDetailRepository.findByTahunAndBulan(tahun, bulan, pageable);
        } else if (nama != null && bulan != null) {
            salesDetailPage = salesDetailRepository.findByNamaAndBulanContainingIgnoreCase(nama, bulan, pageable);
        } else if (tahun != null) {
            salesDetailPage = salesDetailRepository.findByTahun(tahun, pageable);
        } else if (bulan != null) {
            salesDetailPage = salesDetailRepository.findByBulan(bulan, pageable);
        } else if (nama != null) {
            salesDetailPage = salesDetailRepository.findByNamaContainingIgnoreCase(nama, pageable);
        } else {
            salesDetailPage = salesDetailRepository.findAll(pageable);
        }

        // Calculate max values for normalizing scores
        double maxNilaiAchivementTotal = getMaxNilai("Achivement Total");
        double maxNilaiAchivementGadus = getMaxNilai("Achivement Gadus");
        double maxNilaiAchivementPremium = getMaxNilai("Achivement Premium");
        double maxNilaiJumlahVisit = getMaxNilai("Jumlah Visit");
        double maxNilaiJumlahCustomer = getMaxNilai("Jumlah Customer");

        List<RankBulanDto> resultList = new ArrayList<>();

        // Loop through the SalesDetails and map them to RankBulanDto
        for (SalesDetail salesDetail : salesDetailPage) {
            Sales sales = salesDetail.getSales();
            Karyawan karyawan = sales.getKaryawan();

            RankBulanDto rankBulanDto = new RankBulanDto();
            rankBulanDto.setIdsales(sales.getIdsales());
            rankBulanDto.setNama(karyawan.getNama());
            rankBulanDto.setTahun(sales.getTahun());
            rankBulanDto.setBulan(salesDetail.getBulan());
            rankBulanDto.setId(salesDetail.getId());

            // Normalize and apply weights to scores
            double achivementTotal = roundToTwoDecimalPlaces(getNilaiForAchivement("Achivement Total", salesDetail.getTercapaipersenntotal()) / maxNilaiAchivementTotal * getBobotForKriteria("Achivement Total"));
            double achivementGadus = roundToTwoDecimalPlaces(getNilaiForAchivement("Achivement Gadus", salesDetail.getTercapaipersenngadus()) / maxNilaiAchivementGadus * getBobotForKriteria("Achivement Gadus"));
            double achivementPremium = roundToTwoDecimalPlaces(getNilaiForAchivement("Achivement Premium", salesDetail.getTercapaipersennpremium()) / maxNilaiAchivementPremium * getBobotForKriteria("Achivement Premium"));
            double jumlahVisit = roundToTwoDecimalPlaces(getNilaiForAchivement("Jumlah Visit", salesDetail.getJumlahvisit()) / maxNilaiJumlahVisit * getBobotForKriteria("Jumlah Visit"));
            double jumlahCustomer = roundToTwoDecimalPlaces(getNilaiForAchivement("Jumlah Customer", sales.getJumlahcustomer()) / maxNilaiJumlahCustomer * getBobotForKriteria("Jumlah Customer"));

            // Calculate total score
            double hasil = roundToTwoDecimalPlaces(achivementTotal + achivementGadus + achivementPremium + jumlahVisit + jumlahCustomer);
            rankBulanDto.setAchivementtotal(achivementTotal);
            rankBulanDto.setAchivementgadus(achivementGadus);
            rankBulanDto.setAchivementpremium(achivementPremium);
            rankBulanDto.setJumvisit(jumlahVisit);
            rankBulanDto.setJumcustomer(jumlahCustomer);
            rankBulanDto.setHasil(hasil);

            resultList.add(rankBulanDto);
        }

        // Sort resultList by hasil and assign ranks
        resultList.sort((r1, r2) -> Double.compare(r2.getHasil(), r1.getHasil()));
        for (int i = 0; i < resultList.size(); i++) {
            resultList.get(i).setRank(i + 1);
        }

        // Return as a paginated result
        return new PageImpl<>(resultList, pageable, salesDetailPage.getTotalElements());
    }




}
