
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan;

import database.connection;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nurul
 */
public class DataSiswa extends javax.swing.JFrame {

    private final Connection conn = connection.getConn();
    private DefaultTableModel model;
    private String tgl;

    /**
     * Creates new form DataReservasi
     */
    public DataSiswa() {
        initComponents();
        model = new DefaultTableModel();

        tabelsiswa.setModel(model);

        model.addColumn("NIS");
        model.addColumn("Nama Siswa");
        model.addColumn("Tempat Lahir");
        model.addColumn("Tanggal Lahir");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Agama");
        model.addColumn("No.HP");
        model.addColumn("Alamat");
        model.addColumn("Kelas");
        model.addColumn("Status");
        loadData();
        tampilpilih();
    }

    public void FilterAngka(KeyEvent a) {
        if (Character.isAlphabetic(a.getKeyChar())) {
            a.consume();
            JOptionPane.showMessageDialog(null, "Masukan Angka Saja!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    public final void loadData() {
        btSimpan.setEnabled(true);
        btHapus.setEnabled(false);
        btEdit.setEnabled(false);
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            try (Statement s = conn.createStatement()) {
                String sql = "SELECT * FROM siswa";
                try (ResultSet r = s.executeQuery(sql)) {
                    while (r.next()) {
                        Object[] o = new Object[10];
                        o[0] = r.getString("nis");
                        o[1] = r.getString("namasiswa");
                        o[2] = r.getString("tempatlahir");
                        o[3] = r.getString("tgllahir");
                        o[4] = r.getString("jeniskelamin");
                        o[5] = r.getString("agama");
                        o[6] = r.getString("nohp");
                        o[7] = r.getString("alamat");
                        o[8] = r.getString("namakelas");
                        o[9] = r.getString("status");

                        model.addRow(o);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR : \n" + e.toString(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tampilpilih() {
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT DISTINCT namakelas FROM kelas";
            ResultSet r = st.executeQuery(sql);

            while (r.next()) {
                cbkelas.addItem(r.getString("namakelas"));
            }

            r.last();
            r.first();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR : \n" + e.toString(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void carisiswa() {
        String cari = tcari.getText();
        Object[] Baris = {"NIS", "Nama Siswa", "Tempat Lahir", "Tanggal Lahir", "Jenis Kelamin", "Agama","No.HP", "Alamat", "Kelas", "Status"};
        model = new DefaultTableModel(null, Baris);
        tabelsiswa.setModel(model);

        //untuk menampilkan di table
        try {
            String sql = "SELECT * from siswa WHERE "
                    + "nis like '%" + cari + "%' "
                    + "OR namasiswa like '%" + cari + "%' "
                    + "OR tempatlahir like '%" + cari + "%' "
                    + "OR tgllahir like '%" + cari + "%' "
                    + "OR jeniskelamin like '%" + cari + "%' "
                    + "OR agama like '%" + cari + "%' "
                    + "OR nohp like '%" + cari + "%' "
                    + "OR alamat like '%" + cari + "%' "
                    + "OR namakelas like '%" + cari + "%' "
                    + "OR status like '%" + cari + "%' "
                    + "order by nis asc";
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet rslt = stmt.executeQuery(sql);
            while (rslt.next()) {
                String nis = rslt.getString("nis");
                String nama = rslt.getString("namasiswa");
                String tempat = rslt.getString("tempatlahir");
                String tanggallhr = rslt.getString("tgllahir");
                String jkel = rslt.getString("jeniskelamin");
                String agama = rslt.getString("agama");
                String nohp = rslt.getString("nohp");
                String alamat = rslt.getString("alamat");
                String kelas = rslt.getString("namakelas");
                String status = rslt.getString("status");

                String[] dataField = {nis, nama, tempat, tanggallhr, jkel, agama, nohp, alamat, kelas, status};
                model.addRow(dataField);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR : \n" + e.toString(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void kosong() {
        tnamasiswa.setText("");
        ttempat.setText("");
        tnis.setText("");
        ttelp.setText("");
        talamat.setText("");
        tgllahir.setDate(null);
        btjkel.clearSelection();
        cbagama.setSelectedItem("Pilih");
        cbkelas.setSelectedItem("Pilih");
        cbstatus.setSelectedItem("Pilih");
    }

    protected void aktif() {
        tnis.requestFocus();       // Pengecekan apakah textfield aktif atau tidak

        btSimpan.setEnabled(true);
        btEdit.setEnabled(false);
        btHapus.setEnabled(false);
        btBatal.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btjkel = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tnis = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tnamasiswa = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ttelp = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbkelas = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        ttempat = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        talamat = new javax.swing.JTextArea();
        btSimpan = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        btBatal = new javax.swing.JButton();
        btHapus = new javax.swing.JButton();
        rblaki = new javax.swing.JRadioButton();
        rbperempuan = new javax.swing.JRadioButton();
        cbstatus = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cbagama = new javax.swing.JComboBox<>();
        tgllahir = new com.toedter.calendar.JDateChooser();
        btKembali = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelsiswa = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        tcari = new javax.swing.JTextField();
        btcari = new javax.swing.JButton();
        btrefresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Data Siswa");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Input Data Siswa"));

        jLabel1.setText("NIS");

        jLabel2.setText("Nama Siswa");

        jLabel4.setText("Jenis Kelamin");

        jLabel5.setText("Tanggal Lahir");

        jLabel8.setText("No.HP");

        jLabel6.setText("Status");

        jLabel7.setText("Kelas");

        cbkelas.setBackground(new java.awt.Color(28, 31, 44));
        cbkelas.setForeground(new java.awt.Color(255, 255, 255));
        cbkelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih" }));

        jLabel9.setText("Tempat Lahir");

        jLabel10.setText("Alamat");

        talamat.setColumns(20);
        talamat.setRows(5);
        jScrollPane2.setViewportView(talamat);

        btSimpan.setBackground(new java.awt.Color(28, 31, 44));
        btSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btSimpan.setText("Simpan");
        btSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSimpanActionPerformed(evt);
            }
        });

        btEdit.setBackground(new java.awt.Color(28, 31, 44));
        btEdit.setForeground(new java.awt.Color(255, 255, 255));
        btEdit.setText("Edit");
        btEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditActionPerformed(evt);
            }
        });

        btBatal.setBackground(new java.awt.Color(28, 31, 44));
        btBatal.setForeground(new java.awt.Color(255, 255, 255));
        btBatal.setText("Batal");
        btBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBatalActionPerformed(evt);
            }
        });

        btHapus.setBackground(new java.awt.Color(28, 31, 44));
        btHapus.setForeground(new java.awt.Color(255, 255, 255));
        btHapus.setText("Hapus");
        btHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHapusActionPerformed(evt);
            }
        });

        btjkel.add(rblaki);
        rblaki.setText("Laki-laki");

        btjkel.add(rbperempuan);
        rbperempuan.setText("Perempuan");

        cbstatus.setBackground(new java.awt.Color(28, 31, 44));
        cbstatus.setForeground(new java.awt.Color(255, 255, 255));
        cbstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aktif", "Keluar", "Lulus" }));

        jLabel12.setText("Agama");

        cbagama.setBackground(new java.awt.Color(28, 31, 44));
        cbagama.setForeground(new java.awt.Color(255, 255, 255));
        cbagama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Islam", "Kristen", "Budha", "Hindu", "Konghucu" }));

        tgllahir.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tgllahirPropertyChange(evt);
            }
        });

        btKembali.setBackground(new java.awt.Color(28, 31, 44));
        btKembali.setForeground(new java.awt.Color(255, 255, 255));
        btKembali.setText("Kembali");
        btKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btKembaliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel9)
                    .addComponent(jLabel5)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel12)
                        .addComponent(jLabel4)))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbagama, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tnis, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tnamasiswa)
                    .addComponent(ttempat)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rblaki)
                        .addGap(18, 18, 18)
                        .addComponent(rbperempuan))
                    .addComponent(tgllahir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(113, 113, 113)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(jLabel7))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ttelp, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cbkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(64, 64, 64)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                    .addComponent(btEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btBatal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btHapus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                    .addComponent(btKembali, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ttelp)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbkelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btSimpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btEdit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btBatal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btHapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tnis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10)
                            .addComponent(tnamasiswa)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ttempat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tgllahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rblaki)
                            .addComponent(rbperempuan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbagama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(22, 22, 22))))
        );

        tabelsiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelsiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelsiswaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelsiswa);

        jLabel11.setText("Cari Data Siswa");

        tcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tcariActionPerformed(evt);
            }
        });

        btcari.setBackground(new java.awt.Color(28, 31, 44));
        btcari.setForeground(new java.awt.Color(255, 255, 255));
        btcari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loupe.png"))); // NOI18N
        btcari.setText("Cari");
        btcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcariActionPerformed(evt);
            }
        });

        btrefresh.setBackground(new java.awt.Color(28, 31, 44));
        btrefresh.setForeground(new java.awt.Color(255, 255, 255));
        btrefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        btrefresh.setText("Refresh");
        btrefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btrefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btcari)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btrefresh))
                    .addComponent(jScrollPane1))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btcari)
                    .addComponent(btrefresh))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcariActionPerformed
        // TODO add your handling code here:
        carisiswa();
    }//GEN-LAST:event_btcariActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        // TODO add your handling code here:
        String jenis = null;
        if (rblaki.isSelected()) {
            jenis = "Laki-laki";
        } else if (rbperempuan.isSelected()) {
            jenis = "Perempuan";
        }
        try {
            String sql = "UPDATE siswa SET nis=? ,namasiswa=?, tempatlahir=?, tgllahir=?, jeniskelamin=?, agama=?, nohp=?, alamat=?, namakelas=?, status=? where nis='" + tnis.getText() + "'";
            PreparedStatement stat = conn.prepareStatement(sql);

            stat.setString(1, tnis.getText());
            stat.setString(2, tnamasiswa.getText());
            stat.setString(3, ttempat.getText());
            stat.setString(4, tgl);
            stat.setString(5, jenis);
            stat.setString(6, cbagama.getSelectedItem().toString());
            stat.setString(7, ttelp.getText());
            stat.setString(8, talamat.getText());
            stat.setString(9, cbkelas.getSelectedItem().toString());
            stat.setString(10, cbstatus.getSelectedItem().toString());

            stat.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            kosong();
            aktif();
            loadData();
            btSimpan.setEnabled(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Diubah" + e);
        }
    }//GEN-LAST:event_btEditActionPerformed

    private void btSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSimpanActionPerformed
        if (tnis.getText().equals("") || tnamasiswa.getText().equals("") || ttempat.getText().equals("") || talamat.getText().equals("") || ttelp.getText().equals("")
                || tgllahir.getDate() == null || cbstatus.getSelectedItem().equals("") || cbkelas.getSelectedItem().equals("") || talamat.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Lengkapi Data!", "SDIT Bahrul Fikri", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String jenis = null;
            if (rblaki.isSelected()) {
                jenis = "Laki-laki";
            } else if (rbperempuan.isSelected()) {
                jenis = "Perempuan";
            }
            String nis = tnis.getText();
            String nama = tnamasiswa.getText();
            String tempat = ttempat.getText();
            String agama = (String) cbagama.getSelectedItem();
            String telp = ttelp.getText();
            String kelas = (String) cbkelas.getSelectedItem();
            String status = (String) cbstatus.getSelectedItem();
            String alamat = talamat.getText();

            try {
                String sql = "INSERT INTO siswa VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement p = conn.prepareStatement(sql)) {
                    p.setString(1, nis);
                    p.setString(2, nama);
                    p.setString(3, tempat);
                    p.setString(4, tgl);
                    p.setString(5, jenis);
                    p.setString(6, agama);
                    p.setString(7, telp);
                    p.setString(8, alamat);
                    p.setString(9, kelas);
                    p.setString(10, status);

                    p.executeUpdate();
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "ERROR : \n" + e.toString(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
            } finally {
                loadData();
                kosong();
                aktif();
                JOptionPane.showMessageDialog(null, "Data Berhasil Tersimpan", "SDIT Bahrul Fikri", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btSimpanActionPerformed

    private void tabelsiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsiswaMouseClicked
        // TODO add your handling code here:
        btSimpan.setEnabled(false);
        btEdit.setEnabled(true);
        btHapus.setEnabled(true);
        try {
            int bar = tabelsiswa.getSelectedRow();
            String a = model.getValueAt(bar, 0).toString();
            String b = model.getValueAt(bar, 1).toString();
            String c = model.getValueAt(bar, 2).toString();
            String d = model.getValueAt(bar, 3).toString();
            try {
                SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date dt = frmt.parse(d);
                tgllahir.setDate(dt);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "ERROR : \n" + e.toString(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }
            String e = model.getValueAt(bar, 4).toString();
            String f = model.getValueAt(bar, 5).toString();
            String g = model.getValueAt(bar, 6).toString();
            String h = model.getValueAt(bar, 7).toString();
            String i = model.getValueAt(bar, 8).toString();
            String j = model.getValueAt(bar, 9).toString();

            tnis.setText(a);
            tnamasiswa.setText(b);
            ttempat.setText(c);
            if ("Laki-laki".equals(e)) {
                rblaki.setSelected(true);
            } else {
                rbperempuan.setSelected(true);
            }
            cbagama.setSelectedItem(f);
            ttelp.setText(g);
            talamat.setText(h);
            cbkelas.setSelectedItem(i);
            cbstatus.setSelectedItem(j);
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, "ERROR : \n" + e.toString(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tabelsiswaMouseClicked

    private void btBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBatalActionPerformed
        kosong();
        aktif();
        btSimpan.setEnabled(true);
        btEdit.setEnabled(false);
    }//GEN-LAST:event_btBatalActionPerformed

    private void btHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHapusActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "Ingin Menghapus Data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {

            try {
                String sql = "DELETE FROM siswa WHERE nis='" + tnis.getText() + "'";

                java.sql.PreparedStatement ps = conn.prepareStatement(sql);
                ps.execute();
                JOptionPane.showMessageDialog(this, "Berhasil dihapus");
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(this, "ERROR : \n" + e.toString(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }
            loadData();
            kosong();
            aktif();
        }
    }//GEN-LAST:event_btHapusActionPerformed

    private void tcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcariActionPerformed
        carisiswa();
    }//GEN-LAST:event_tcariActionPerformed

    private void btrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btrefreshActionPerformed
        kosong();
        loadData();
        tcari.setText("");
    }//GEN-LAST:event_btrefreshActionPerformed

    private void tgllahirPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tgllahirPropertyChange
        if (tgllahir.getDate() != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            tgl = format.format(tgllahir.getDate());
        }
    }//GEN-LAST:event_tgllahirPropertyChange

    private void btKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btKembaliActionPerformed
        new Utama().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btKembaliActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */


 /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new DataSiswa().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBatal;
    private javax.swing.JButton btEdit;
    private javax.swing.JButton btHapus;
    private javax.swing.JButton btKembali;
    private javax.swing.JButton btSimpan;
    private javax.swing.JButton btcari;
    private javax.swing.ButtonGroup btjkel;
    private javax.swing.JButton btrefresh;
    private javax.swing.JComboBox<String> cbagama;
    private javax.swing.JComboBox<String> cbkelas;
    private javax.swing.JComboBox<String> cbstatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rblaki;
    private javax.swing.JRadioButton rbperempuan;
    private javax.swing.JTable tabelsiswa;
    private javax.swing.JTextArea talamat;
    private javax.swing.JTextField tcari;
    private com.toedter.calendar.JDateChooser tgllahir;
    private javax.swing.JTextField tnamasiswa;
    private javax.swing.JTextField tnis;
    private javax.swing.JTextField ttelp;
    private javax.swing.JTextField ttempat;
    // End of variables declaration//GEN-END:variables
}
