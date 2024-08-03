package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.CptDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.PicosDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Cpt;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Picos;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.repository.KaryawanRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.PicosRepository;
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
public class PicosService {
    private final PicosRepository picosRepository;
    private final KaryawanRepository karyawanRepository;

    public ResponseEntity<Object> createPicos(PicosDto picosDto, String nik) {
        log.info("Inside createPicos");
        try {
            Picos picos = new Picos();
            picos.setBulan(picosDto.getBulan());
            picos.setTahun(picosDto.getTahun());
            Karyawan karyawan = karyawanRepository.findByNik(picosDto.getNik());
            if (karyawan == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Karyawan not found for NIK: " + picosDto.getNik());
            }
            picos.setKaryawan(karyawan);
            picos.setPipelinestrength(picosDto.getPipelinestrength());
            picos.setLowtouchratio(picosDto.getLowtouchratio());
            picos.setCrosssellratio(picosDto.getCrosssellratio());
            picos.setPremiumcontribution(picosDto.getPremiumcontribution());
            picosRepository.save(picos);

            return ResponseEntity.ok("Picos created successfully");
        } catch (Exception e) {
            log.error("Error creating Picos", e);
            return ResponseEntity.status(500).body("Error creating picos");
        }
    }

    public ResponseEntity<Object> editPicos(Long id, PicosDto picosDto) {
        log.info("Inside editPicos");
        try {
            Optional<Picos> optionalPicos = picosRepository.findById(id);
            if (!optionalPicos.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Picos not found with id " + id);
            }
            Picos picos = optionalPicos.get();
            picos.setBulan(picosDto.getBulan());
            picos.setTahun(picosDto.getTahun());
            picos.setPipelinestrength(picosDto.getPipelinestrength());
            picos.setLowtouchratio(picosDto.getLowtouchratio());
            picos.setCrosssellratio(picosDto.getCrosssellratio());
            picos.setPremiumcontribution(picosDto.getPremiumcontribution());
            picosRepository.save(picos);


            return ResponseEntity.ok("Picos edited successfully");
        } catch (Exception e) {
            log.error("Error editing picos", e);
            return ResponseEntity.status(500).body("Error editing picos");
        }
    }

    public ResponseEntity<Object> deletePicos(Long id) {
        log.info("Inside deletePicos");
        try {
            Optional<Picos> optionalPicos = picosRepository.findById(id);

            if (optionalPicos.isPresent()) {
                picosRepository.deleteById(id);
                return ResponseEntity.ok("Successfully deleted data picos");
            } else {
                return ResponseEntity.status(404).body("data picos not found");
            }
        } catch (Exception e) {
            log.error("Error delete data picos", e);
            return ResponseEntity.status(500).body("Error delete data picos");
        }
    }

    public Page<PicosDto> showAllAndPaginationPicos(String nama,Integer tahun, String order, int offset, int pageSize) {
        log.info("Inside showAllAndPaginationPicos");
        Page<Picos> picosPage;
        if (tahun != null && nama != null) {
            picosPage = picosRepository.findByTahunAndNamaContainingIgnoreCase(tahun,nama,PageRequest.of(offset - 1, pageSize,  "desc".equals(order) ? Sort.by("idpicos").descending() : Sort.by("idpicos").ascending()));
        } else if (nama != null) {
            picosPage = picosRepository.findByNamaContainingIgnoreCase(nama,PageRequest.of(offset - 1, pageSize,  "desc".equals(order) ? Sort.by("idpicos").descending() : Sort.by("idpicos").ascending()));
        } else if (tahun != null) {
            picosPage = picosRepository.findByTahun(tahun,PageRequest.of(offset - 1, pageSize,  "desc".equals(order) ? Sort.by("idpicos").descending() : Sort.by("idpicos").ascending()));
        } else {
            picosPage = picosRepository.findAll(PageRequest.of(offset - 1, pageSize,  "desc".equals(order) ? Sort.by("idpicos").descending() : Sort.by("idpicos").ascending()));
        }
        List<PicosDto> resultList = picosPage.getContent().stream()
                .map(picos -> {
                    PicosDto picosDto = new PicosDto();
                    picosDto.setIdpicos(picos.getIdpicos());
                    picosDto.setNik(picos.getKaryawan().getNik());
                    picosDto.setNama(picos.getKaryawan().getNama());
                    picosDto.setBulan(picos.getBulan());
                    picosDto.setTahun(picos.getTahun());
                    picosDto.setPipelinestrength(picos.getPipelinestrength());
                    picosDto.setLowtouchratio(picos.getLowtouchratio());
                    picosDto.setCrosssellratio(picos.getCrosssellratio());
                    picosDto.setPremiumcontribution(picos.getPremiumcontribution());
                    return picosDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, picosPage.getPageable(), picosPage.getTotalElements());
    }

    public PicosDto fetchPicosDtoByIdpicos(Long id) throws AllException {
        log.info("Inside fetchPicosDtoByIdpicos");
        Picos picos = picosRepository.findById(id).orElseThrow(() -> new AllException("Picos with idpicos " + id + " not found"));

        PicosDto picosDto = new PicosDto();
        Karyawan karyawan = picos.getKaryawan();
        picosDto.setIdpicos(picos.getIdpicos());
        picosDto.setNik(karyawan.getNik());
        picosDto.setNama(karyawan.getNama());
        picosDto.setBulan(picos.getBulan());
        picosDto.setTahun(picos.getTahun());
        picosDto.setPipelinestrength(picos.getPipelinestrength());
        picosDto.setLowtouchratio(picos.getLowtouchratio());
        picosDto.setCrosssellratio(picos.getCrosssellratio());
        picosDto.setPremiumcontribution(picos.getPremiumcontribution());
        return picosDto;
    }
}
