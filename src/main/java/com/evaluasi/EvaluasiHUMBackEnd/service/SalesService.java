package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.SalesDetailDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.SalesDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.UserDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Sales;
import com.evaluasi.EvaluasiHUMBackEnd.entity.SalesDetail;
import com.evaluasi.EvaluasiHUMBackEnd.entity.User;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.repository.KaryawanRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.SalesDetailRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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


    public ResponseEntity<Object> createSales(SalesDto salesDto, String nik) {
        log.info("inside createSales", salesDto);
        try {
            Sales sales = new Sales();
            Karyawan karyawan = karyawanRepository.findByNik(salesDto.getNik());
            if (karyawan == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Karyawan not found for NIK: " + salesDto.getNik());
            }
            sales.setKaryawan(karyawan);
            sales.setTarget(salesDto.getTarget());
            sales.setTahun(salesDto.getTahun());

            salesRepository.save(sales);

                for (SalesDetailDto salesDetailDto : salesDto.getSalesDetailDtoList()) {
                    SalesDetail salesDetail = new SalesDetail();
                    salesDetail.setBulan(salesDetailDto.getBulan());
                    salesDetail.setTargetbln(salesDetailDto.getTargetbln());
                    salesDetail.setSales(sales);
                    salesDetailRepository.save(salesDetail);
                }


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
            sales.setTarget(salesDto.getTarget());
            sales.setTahun(salesDto.getTahun());

            salesRepository.save(sales);

            List<SalesDetailDto> salesDetailDtoList = salesDto.getSalesDetailDtoList();
            if (salesDetailDtoList != null) {
                for (SalesDetailDto salesDetailDto : salesDetailDtoList) {
                    Optional<SalesDetail> optionalSalesDetail = salesDetailRepository.findById(salesDetailDto.getId());
                    if (optionalSalesDetail.isPresent()) {
                        // If SalesDetail with the given ID exists, update it
                        SalesDetail salesDetail = optionalSalesDetail.get();
                        salesDetail.setBulan(salesDetailDto.getBulan());
                        salesDetail.setTargetbln(salesDetailDto.getTargetbln());
                        salesDetailRepository.save(salesDetail);
                    } else {
                        // If SalesDetail with the given ID doesn't exist, create new SalesDetail
                        SalesDetail salesDetail = new SalesDetail();
                        salesDetail.setBulan(salesDetailDto.getBulan());
                        salesDetail.setTargetbln(salesDetailDto.getTargetbln());
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
            Optional<Sales> optionalSales  = salesRepository.findById(id);

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

    public Page<SalesDto> showAllAndPaginationSales(Integer target, Integer tahun, String order, int offset, int pageSize) {
        log.info("Inside showAllAndPaginationSales");
        Page<Sales> salesPage;
        if (target != null && tahun != null) {
            salesPage = salesRepository.findByTahunAndTarget(tahun, target, PageRequest.of(offset - 1, pageSize, "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (target != null) {
            salesPage = salesRepository.findByTarget(target, PageRequest.of(offset - 1, pageSize, "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else if (tahun != null) {
            salesPage = salesRepository.findByTahun(tahun, PageRequest.of(offset - 1, pageSize, "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        } else {
            salesPage = salesRepository.findAll(PageRequest.of(offset - 1, pageSize, "desc".equals(order) ? Sort.by("idsales").descending() : Sort.by("idsales").ascending()));
        }
        List<SalesDto> resultList = salesPage.getContent().stream()
                .map(sales -> {
                    SalesDto salesDto = new SalesDto();
                    Karyawan karyawan = sales.getKaryawan();
                    salesDto.setIdsales(sales.getIdsales());
                    salesDto.setNik(karyawan.getNik());
                    salesDto.setNama(karyawan.getNama());
                    salesDto.setTarget(sales.getTarget());
                    salesDto.setTahun(sales.getTahun());

                    // Map SalesDetail information
                    List<SalesDetailDto> salesDetailDtoList = sales.getSalesDetails().stream()
                            .map(saless -> {
                                SalesDetailDto salesDetailDto = new SalesDetailDto();
                                salesDetailDto.setId(saless.getId());
                                salesDetailDto.setBulan(saless.getBulan());
                                salesDetailDto.setTargetbln(saless.getTargetbln());
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

    public SalesDto fetchSalesDtoByIdsales(Long id) throws AllException {
        log.info("Inside fetchUserDtoByIduser");
        Sales sales = salesRepository.findById(id)
                .orElseThrow(() -> new AllException("Sales with idsales " + id + " not found"));

            SalesDto salesDto = new SalesDto();
            Karyawan karyawan = sales.getKaryawan();
            salesDto.setIdsales(sales.getIdsales());
            salesDto.setNik(karyawan.getNik());
            salesDto.setNama(karyawan.getNama());
            salesDto.setTarget(sales.getTarget());
            salesDto.setTahun(sales.getTahun());

        List<SalesDetailDto> salesDetailDtoList = sales.getSalesDetails().stream()
                .map(saless -> {
                    SalesDetailDto salesDetailDto = new SalesDetailDto();
                    salesDetailDto.setId(saless.getId());
                    salesDetailDto.setBulan(saless.getBulan());
                    salesDetailDto.setTargetbln(saless.getTargetbln());
                    salesDetailDto.setIdsales(saless.getSales().getIdsales());
                    return salesDetailDto;
                })
                .collect(Collectors.toList());
        salesDto.setSalesDetailDtoList(salesDetailDtoList);

            return salesDto;
    }


}


