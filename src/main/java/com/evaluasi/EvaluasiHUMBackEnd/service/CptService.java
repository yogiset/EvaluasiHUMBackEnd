package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.CptDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Cpt;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
import com.evaluasi.EvaluasiHUMBackEnd.entity.SalesDetail;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.repository.CptRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.KaryawanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CptService {
    private final CptRepository cptRepository;
    private final KaryawanRepository karyawanRepository;

    public ResponseEntity<Object> createCpt(CptDto cptDto, String nik) {
        log.info("Inside createCpt");
        try {
            Cpt cpt = new Cpt();

            cpt.setTahun(cptDto.getTahun());
            Karyawan karyawan = karyawanRepository.findByNik(cptDto.getNik());
            if (karyawan == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Karyawan not found for NIK: " + cptDto.getNik());
            }
            cpt.setKaryawan(karyawan);
            cpt.setPanolcustomer(cptDto.getPanolcustomer());
            cpt.setCoverage(cptDto.getCoverage());

            double overallPercentage = (cpt.getCoverage() * 100.0) / (cpt.getPanolcustomer());

            cpt.setCoveragepersen(overallPercentage);
            cpt.setPenetration(cptDto.getPenetration());
            cpt.setThroughput(cptDto.getThroughput());
            cpt.setHitrate(cptDto.getHitrate());
            cptRepository.save(cpt);


            return ResponseEntity.ok("Cpt created successfully");
        } catch (Exception e) {
            log.error("Error creating cpt", e);
            return ResponseEntity.status(500).body("Error creating cpt");
        }
    }

    public ResponseEntity<Object> editCpt(Long id, CptDto cptDto) {
        log.info("Inside editCpt");
        try {
        Optional<Cpt> optionalCpt = cptRepository.findById(id);
        if (!optionalCpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cpt not found with id " + id);
        }
        Cpt cpt = optionalCpt.get();
        cpt.setPanolcustomer(cptDto.getPanolcustomer());
        cpt.setCoverage(cptDto.getCoverage());

        double overallPercentage = (cpt.getCoverage() * 100.0) / (cpt.getPanolcustomer());
        cpt.setCoveragepersen(overallPercentage);

        cpt.setPenetration(cptDto.getPenetration());
        cpt.setThroughput(cptDto.getThroughput());
        cpt.setHitrate(cptDto.getHitrate());
        cptRepository.save(cpt);


        return ResponseEntity.ok("Cpt edited successfully");
    } catch (Exception e) {
        log.error("Error editing cpt", e);
        return ResponseEntity.status(500).body("Error editing cpt");
    }
    }

    public ResponseEntity<Object> deleteCpt(Long id) {
        log.info("Inside deleteCpt");
        try {
            Optional<Cpt> optionalCpt = cptRepository.findById(id);

            if (optionalCpt.isPresent()) {
                cptRepository.deleteById(id);
                return ResponseEntity.ok("Successfully deleted data cpt");
            } else {
                return ResponseEntity.status(404).body("data cpt not found");
            }
        } catch (Exception e) {
            log.error("Error delete data cpt", e);
            return ResponseEntity.status(500).body("Error delete data cpt");
        }
    }

    public Page<CptDto> showAllAndPaginationCpt(Integer coveragepersen, Integer tahun, String order, int offset, int pageSize) {
        log.info("Inside showAllAndPaginationCpt");
        Page<Cpt> cptPage;
        if (coveragepersen != null && tahun != null) {
            cptPage = cptRepository.findByTahunAndCoveragepersen(coveragepersen,tahun,PageRequest.of(offset - 1, pageSize,  "desc".equals(order) ? Sort.by("idcpt").descending() : Sort.by("idcpt").ascending()));
        } else if (coveragepersen != null) {
            cptPage = cptRepository.findByCoveragePersen(coveragepersen,PageRequest.of(offset - 1, pageSize,  "desc".equals(order) ? Sort.by("idcpt").descending() : Sort.by("idcpt").ascending()));
        } else if (tahun != null) {
            cptPage = cptRepository.findByTahun(tahun,PageRequest.of(offset - 1, pageSize,  "desc".equals(order) ? Sort.by("idcpt").descending() : Sort.by("idcpt").ascending()));
        } else {
            cptPage = cptRepository.findAll(PageRequest.of(offset - 1,pageSize, "desc".equals(order) ? Sort.by("idcpt").descending() : Sort.by("idcpt").ascending()));
        }
        List<CptDto> resultList = cptPage.getContent().stream()
                .map(cpt -> {
                    CptDto cptDto = new CptDto();
                    cptDto.setIdcpt(cpt.getIdcpt());
                    cptDto.setNik(cpt.getKaryawan().getNik());
                    cptDto.setNama(cpt.getKaryawan().getNama());
                    cptDto.setTahun(cpt.getTahun());
                    cptDto.setPanolcustomer(cpt.getPanolcustomer());
                    cptDto.setCoverage(cpt.getCoverage());
                    cptDto.setCoveragepersen(cpt.getCoveragepersen());
                    cptDto.setPenetration(cpt.getPenetration());
                    cptDto.setThroughput(cpt.getThroughput());
                    cptDto.setHitrate(cpt.getHitrate());
                    return cptDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, cptPage.getPageable(), cptPage.getTotalElements());
    }

    public CptDto fetchCptDtoByIdcpt(Long id) throws AllException {
        log.info("Inside fetchCptDtoByIdcpt");
        Cpt cpt = cptRepository.findById(id).orElseThrow(() -> new AllException("Cpt with idcpt " + id + " not found"));

                    CptDto cptDto = new CptDto();
                    Karyawan karyawan = cpt.getKaryawan();
                    cptDto.setIdcpt(cpt.getIdcpt());
                    cptDto.setNik(karyawan.getNik());
                    cptDto.setNama(karyawan.getNama());
                    cptDto.setTahun(cpt.getTahun());
                    cptDto.setPanolcustomer(cpt.getPanolcustomer());
                    cptDto.setCoverage(cpt.getCoverage());
                    cptDto.setCoveragepersen(cpt.getCoveragepersen());
                    cptDto.setPenetration(cpt.getPenetration());
                    cptDto.setThroughput(cpt.getThroughput());
                    cptDto.setHitrate(cpt.getHitrate());
                    return cptDto;

    }
}
