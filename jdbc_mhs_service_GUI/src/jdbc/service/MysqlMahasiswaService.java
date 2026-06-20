package jdbc.service;

import jdbc.model.Mahasiswa;
import jdbc.utilities.MysqlUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MysqlMahasiswaService {

    Connection koneksi = null;

    public MysqlMahasiswaService() {
        this.koneksi = MysqlUtility.getConnection();
    }

    public Mahasiswa makeMhsObject(ResultSet rs) throws SQLException {
        Mahasiswa mhs = new Mahasiswa();
        mhs.setId(rs.getInt("id"));
        mhs.setNama(rs.getString("nama"));
        return mhs;
    }

    public void add(Mahasiswa mhs) {
        String query =
        "INSERT INTO mahasiswa (nama) VALUES (?)";
        try (PreparedStatement ps =
                koneksi.prepareStatement(query)) {
            ps.setString(1, mhs.getNama());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Mahasiswa mhs) {
        String query = "UPDATE mahasiswa SET nama = ? WHERE id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(query)) {
            ps.setString(1, mhs.getNama());
            ps.setInt(2, mhs.getId());
            ps.executeUpdate();
            System.out.println("Data mahasiswa berhasil diupdate.");
        } catch (SQLException e) {
            System.out.println("Gagal mengupdate data: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM mahasiswa WHERE id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Data mahasiswa berhasil dihapus.");
        } catch (SQLException e) {
            System.out.println("Gagal menghapus data: " + e.getMessage());
        }
    }

    public Mahasiswa getById(int id) {
        String query = "SELECT * FROM mahasiswa WHERE id = ?";
        Mahasiswa mhs = null;
        try (PreparedStatement ps = koneksi.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    mhs = makeMhsObject(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data by ID: " + e.getMessage());
        }
        return mhs;
    }

    public List<Mahasiswa> getAll() {
        String query = "SELECT * FROM mahasiswa";
        List<Mahasiswa> listMhs = new ArrayList<>();
        try (Statement stmt = koneksi.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                listMhs.add(makeMhsObject(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Gagal mengambil semua data: " + e.getMessage());
        }
        return listMhs;
    }

    public void indexReset() {
        String query = "ALTER TABLE mahasiswa AUTO_INCREMENT = 1";
        try (Statement stmt = koneksi.createStatement()) {
            stmt.executeUpdate(query);
            System.out.println("Indeks tabel mahasiswa berhasil direset.");
        } catch (SQLException e) {
            System.out.println("Gagal mereset indeks: " + e.getMessage());
        }
    }

    public boolean isEmpty() {
        String query = "SELECT COUNT(*) FROM mahasiswa";
        try (Statement stmt = koneksi.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            System.out.println("Gagal memeriksa data: " + e.getMessage());
        }
        return false;
    }

    public void closeConnection() {
        if (koneksi == null) {
            return;
        }

        try {
            if (!koneksi.isClosed()) {
                koneksi.close();
                System.out.println("Koneksi database berhasil ditutup.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal menutup koneksi: " + e.getMessage());
        }
    }
}