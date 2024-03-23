package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.KaryawanDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.UserDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.EvaluasiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
        String[] staticColors = {"#1bfe15", "#faf53b", "#e40b2b"};  // Add more colors if needed
        String[] dynamicColors = generateColors(labels.size());
        // Combine static and dynamic colors
        String[] colors = new String[staticColors.length + dynamicColors.length];
        System.arraycopy(staticColors, 0, colors, 0, staticColors.length);
        System.arraycopy(dynamicColors, 0, colors, staticColors.length, dynamicColors.length);

        List<Map<String, Object>> dataChart = new ArrayList<>();
        int dataIndex = 1;
        for (String label : labels) {
            Map<String, Object> chartData = new HashMap<>();
            chartData.put("label", label);

            List<Map<String, Object>> data = new ArrayList<>(); // Separate data for each Hasil Evaluasi
            List<LocalDate> uniqueTanggalEvaluasi = evaluasiService.getUniqueTanggalEvaluasiByHasilEvaluasi(label);
            for (LocalDate tanggalEvaluasi : uniqueTanggalEvaluasi) {
                int count = (int) evaluasiService.getCountForHasilAndTanggalEvaluasi(label, tanggalEvaluasi); // Adjust this method according to your data access logic
                Map<String, Object> dataPoint = new HashMap<>();
                dataPoint.put("primary", DateTimeFormatter.ISO_DATE.format(tanggalEvaluasi));
                dataPoint.put("result", count);
                data.add(dataPoint);
            }
            chartData.put("data", data); // Assign specific data to each Hasil Evaluasi

            int currentResult = 0;
            for (Map<String, Object> dataPoint : data) {
                int count = (int) dataPoint.get("result");
                currentResult += count; // Summing up counts for current Hasil Evaluasi
            }
            chartData.put("currentResult", currentResult);
            chartData.put("color", colors[dataIndex - 1]);

            dataChart.add(chartData);
            dataIndex++;
        }
        apiResponse.put("dataChart", dataChart);

        return apiResponse;
    }

    private String[] generateColors(int numberOfColors) {
        String[] colors = new String[numberOfColors];
        Random rand = new Random();
        for (int i = 0; i < numberOfColors; i++) {
            int r = rand.nextInt(256);
            int g = rand.nextInt(256);
            int b = rand.nextInt(256);
            colors[i] = String.format("#%02x%02x%02x", r, g, b);
        }
        return colors;
    }
}


