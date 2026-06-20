import java.util.LinkedHashMap;
import java.util.Map;

// * File: LambdaMap.java
// * Deskripsi: Implementasi lambda pada Map, digunakan untuk menampilkan
// key dan value (NIM dan nama mahasiswa).

public class LambdaMap {
    public static void main(String[] args) {
        Map<String, String> mahasiswaMap = new LinkedHashMap<>();
        mahasiswaMap.put("24060124130123", "Adi");
        mahasiswaMap.put("24060124130124", "Bambang");
        mahasiswaMap.put("24060124130125", "Cici");
        mahasiswaMap.put("24060124130126", "Didi");

        mahasiswaMap.forEach((nim, nama) -> System.out.println(nim + " - " + nama));
    }
}