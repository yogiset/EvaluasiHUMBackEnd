package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.KaryawanDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.UserDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.EvaluasiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class APIResponseBuilder {

    private final UserService userService;
    private final KaryawanService karyawanService;
    private final EvaluasiService evaluasiService;

    private List<EvaluasiDto> getAllEvaluasi() {
        return new ArrayList<>();
    }
    public Map<String, Object> buildAPIResponse() {
        Map<String, Object> apiResponse = new HashMap<>();

        List<EvaluasiDto> evaluasiList = getAllEvaluasi();

        long totalKaryawan = karyawanService.getTotalKaryawan();
        apiResponse.put("totalKaryawan", totalKaryawan);
        long totalUser = userService.getTotalUsers();
        apiResponse.put("totalUser", totalUser);
        long totalEvaluasi = evaluasiService.getTotalEvaluasi();
        apiResponse.put("totalEvaluasi", totalEvaluasi);

        List<String> labels = evaluasiService.getUniqueHasilEvaluasi();
        String[] colors = {"#1bfe15", "#faf53b", "#e40b2b"};  // Add more colors if needed

        List<Map<String, Object>> dataChart = new ArrayList<>();
        int dataIndex = 1; // Counter for unique data key
        for (String label : labels) {
            Map<String, Object> chartData = new HashMap<>();
            chartData.put("Hasil Evaluasi", label);

            List<Map<String, Object>> data = new ArrayList<>(); // Separate data for each Hasil Evaluasi
            List<LocalDate> uniqueTanggalEvaluasi = evaluasiService.getUniqueTanggalEvaluasiByHasilEvaluasi(label);
            for (LocalDate tanggalEvaluasi : uniqueTanggalEvaluasi) {
                int count = (int) evaluasiService.getCountForHasilAndTanggalEvaluasi(label, tanggalEvaluasi); // Adjust this method according to your data access logic
                Map<String, Object> dataPoint = new HashMap<>();
                dataPoint.put("Tanggal Evaluasi", DateTimeFormatter.ISO_DATE.format(tanggalEvaluasi));
                dataPoint.put("result", count);
                data.add(dataPoint);
            }
            chartData.put("data" + dataIndex, data); // Assign specific data to each Hasil Evaluasi

            int currentResult = 0;
            for (Map<String, Object> dataPoint : data) {
                int count = (int) dataPoint.get("result");
                currentResult += count; // Summing up counts for current Hasil Evaluasi
            }
            chartData.put("currentResult", currentResult);

            dataChart.add(chartData);

            dataIndex++; // Increment data index for next Hasil Evaluasi
        }
        apiResponse.put("dataChart", dataChart);

        return apiResponse;
    }

    private static int indexOf(String[] array, String target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }
}


