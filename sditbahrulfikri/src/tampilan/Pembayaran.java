/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan;


import database.connection;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.*;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author nurul
 */
public class Pembayaran extends javax.swing.JFrame {
    private final Connection conn = connection.getConn();
    private DefaultTableModel model;
    public long total;
    public long bayar;
    public long kembali;

    /**
     * Creates new form PembayaranSiswa
     */
    public Pembayaran() {
        initComponents();
        
        model = new DefaultTableModel();

        tabelpembayaran.setModel(model);
        
        model.addColumn("No.Transaksi");
        model.addColumn("ID Catat");
        model.addColumn("NIS");
        model.addColumn("Nama Siswa");
        model.addColumn("Kelas");
        model.addColumn("Pembayaran");
        model.addColumn("Keterangan");
        model.addColumn("Subtotal");

        loadData();
        tampilpilih();
        tampiltagihan();
        idcatat();
    }
    
     public final void loadData() {
        btTambah.setEnabled(true);
        btHapus.setEnabled(true);
        btEdit.setEnabled(false);
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
            try {
                Connection c = connection.getConn();
            try (Statement s = c.createStatement()) {
                String sql = "SELECT id_tmp, id_catat, nis, namasiswa, kelas, namabayar, keterangan, subtotal FROM tmp_pembayaran";
                    try (ResultSet rs = s.executeQuery(sql)) {
                        while (rs.next()) {
                            Object[] o = new Object[9];
                            o[0] = rs.getString("id_tmp");
                            o[1] = rs.getString("id_catat");
                            o[2] = rs.getString("nis");
                            o[3] = rs.getString("namasiswa");
                            o[4] = rs.getString("kelas");
                            o[5] = rs.getString("namabayar");
                            o[6] = rs.getString("keterangan");
                            o[7] = rs.getString("subtotal");
                            
                                                        
                            model.addRow(o);
                        }   }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR : \n" + e.toString(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
   }
     
    public void FilterAngka(KeyEvent a){
       if(Character.isAlphabetic(a.getKeyChar())){
           a.consume();
           JOptionPane.showMessageDialog(null, "Masukan Angka Saja!", "Peringatan", JOptionPane.WARNING_MESSAGE);
       }
    }
    
    public void caridata(){
       String cari = tcari.getText();
        //String to=jCombo.getItemAt(i).toString();
        
       Object[] baris={"No.Transaksi","ID Catat","NIS","Nama Siswa","Kelas","Pembayaran", "Keterangan","Subtotal", "Total Pembayaran"};
       model = new DefaultTableModel(null, baris);
       tabelpembayaran.setModel(model);

       //untuk menampilkan di table
       try{
           String sql="SELECT * from pembayaran WHERE id_catat like '%"+cari+"%'"                 
                   + " OR nis like '%"+cari+"%' "
                   + " OR namasiswa like '%"+cari+"%' "
                   + "order by no_pembayaran asc";
           java.sql.Statement stmt=conn.createStatement();
           java.sql.ResultSet rslt=stmt.executeQuery(sql);
           while(rslt.next()){
               String no = rslt.getString("no_pembayaran");
               String id = rslt.getString("id_catat");
               String nis = rslt.getString("nis");
               String namasiswa = rslt.getString("namasiswa");
               String kls = rslt.getString("kelas");
               String nb = rslt.getString("namabayar");
               String ket = rslt.getString("keterangan");
               String subtotal = rslt.getString("subtotal");
               String total = rslt.getString("total");
               
               String[] dataField={no, id, nis, namasiswa, kls, nb, ket, subtotal, total};
               model.addRow(dataField);
               btTambah.setEnabled(false);
               btEdit.setEnabled(false);
               btHapus.setEnabled(false);
           }
       }
       catch(SQLException e){
           JOptionPane.showMessageDialog(null, e);
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
            JOptionPane.showMessageDialog(null, e);
        }

    }
      
    public void cetak(){
       try {
            String reportName = connection.PathReport + "strukpembayaran.jasper";
            HashMap parameter = new HashMap();
            File reportFile = new File (reportName);
            parameter.put("id",tcari.getText());
            
            JasperReport jReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
            JasperPrint jPrint = JasperFillManager.fillReport(jReport, parameter, connection.getConn());
            JasperViewer.viewReport(jPrint, false);
     } catch (JRException e) {
         System.out.println(e);
         e.printStackTrace();
     }
   }
      
      private void tampiltagihan() {
        try {           
            Statement st = conn.createStatement();
            String sql = "SELECT DISTINCT namapembayaran FROM jenispembayaran";
            ResultSet r = st.executeQuery(sql);

            while (r.next()) {
                cbpembayaran.addItem(r.getString("namapembayaran")); 
            }

            r.last();
            r.first();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    
    
     private void idcatat() {
        try {
            Connection c = connection.getConn();
            Statement s = c.createStatement();

            String sql = "SELECT * FROM pembayaran ORDER by id_catat desc";
            ResultSet r = s.executeQuery(sql);

            if (r.next()) {
                String nofak = r.getString("id_catat").substring(1);
                String AN = "" + (Integer.parseInt(nofak) + 1);
                String Nol = "";
                switch (AN.length()) {
                    case 1:
                        Nol = "000";
                        break;
                    case 2:
                        Nol = "00";
                        break;
                    case 3:
                        Nol = "0";
                        break;
                    case 4:
                        Nol = "";
                        break;
                    default:
                        break;
                }
                tId.setText("B" + Nol + AN);
            } else {
                tId.setText("B0001");
            }
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
     protected void kosong() {
        tnis.setText(null);
        tnama.setText(null);
        cbkelas.setSelectedItem("Pilih");
        cbpembayaran.setSelectedItem("Pilih");
        rbcarabayar.clearSelection();
        ttotal.setText(null);
        tjumlahbulan.setText(null);
        tket.setText(null);
        ttotalbayar.setText(null);
        tcari.setText(null);
        lbJum.setText(null);
        tBayar.setText(null);
        tKembalian.setText(null);
     }

     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbcarabayar = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tnis = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbpembayaran = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        ttotal = new javax.swing.JTextField();
        btTambah = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        btHapus = new javax.swing.JButton();
        btBatal = new javax.swing.JButton();
        tnama = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        rbangsuran = new javax.swing.JRadioButton();
        rbbulanan = new javax.swing.JRadioButton();
        rbsekali = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        cbkelas = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        tjumlahbulan = new javax.swing.JTextField();
        tket = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        ttotalbayar = new javax.swing.JTextField();
        btHitung = new javax.swing.JButton();
        cbxKet = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelpembayaran = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        tId = new javax.swing.JTextField();
        btSimpan = new javax.swing.JButton();
        tno = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        tcari = new javax.swing.JTextField();
        btcari = new javax.swing.JButton();
        btcetak = new javax.swing.JButton();
        btrefresh = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        lbJum = new javax.swing.JLabel();
        bttotal = new javax.swing.JButton();
        tBayar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        tKembalian = new javax.swing.JTextField();
        btDataBaru = new javax.swing.JButton();
        btKembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pembayaran");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Pembayaran"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Nama Siswa");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        tnis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnisActionPerformed(evt);
            }
        });
        jPanel2.add(tnis, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 140, -1));

        jLabel3.setText("Pembayaran");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 77, -1));
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 173, -1, -1));

        cbpembayaran.setBackground(new java.awt.Color(28, 31, 44));
        cbpembayaran.setForeground(new java.awt.Color(255, 255, 255));
        cbpembayaran.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih" }));
        jPanel2.add(cbpembayaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 144, -1));

        jLabel11.setText("Jumlah Tagihan");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 100, 20));
        jPanel2.add(ttotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, 134, -1));

        btTambah.setBackground(new java.awt.Color(28, 31, 44));
        btTambah.setForeground(new java.awt.Color(255, 255, 255));
        btTambah.setText("Tambah Pembayaran");
        btTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTambahActionPerformed(evt);
            }
        });
        jPanel2.add(btTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 160, -1));

        btEdit.setBackground(new java.awt.Color(28, 31, 44));
        btEdit.setForeground(new java.awt.Color(255, 255, 255));
        btEdit.setText("Edit");
        btEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditActionPerformed(evt);
            }
        });
        jPanel2.add(btEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 470, -1, -1));

        btHapus.setBackground(new java.awt.Color(28, 31, 44));
        btHapus.setForeground(new java.awt.Color(255, 255, 255));
        btHapus.setText("Hapus");
        btHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHapusActionPerformed(evt);
            }
        });
        jPanel2.add(btHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 470, 73, -1));

        btBatal.setBackground(new java.awt.Color(28, 31, 44));
        btBatal.setForeground(new java.awt.Color(255, 255, 255));
        btBatal.setText("Batal");
        btBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBatalActionPerformed(evt);
            }
        });
        jPanel2.add(btBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 470, -1, -1));
        jPanel2.add(tnama, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 230, -1));

        jLabel4.setText("NIS");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 20));

        jLabel7.setText("Cara Pembayaran");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, 23));

        rbcarabayar.add(rbangsuran);
        rbangsuran.setText("Angsuran");
        rbangsuran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbangsuranActionPerformed(evt);
            }
        });
        jPanel2.add(rbangsuran, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, -1, -1));

        rbcarabayar.add(rbbulanan);
        rbbulanan.setText("Bulanan");
        rbbulanan.setToolTipText("");
        rbbulanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbbulananActionPerformed(evt);
            }
        });
        jPanel2.add(rbbulanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, -1, -1));

        rbcarabayar.add(rbsekali);
        rbsekali.setText("1x bayar");
        rbsekali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbsekaliActionPerformed(evt);
            }
        });
        jPanel2.add(rbsekali, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, -1, -1));

        jLabel9.setText("Kelas");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, 20));

        cbkelas.setBackground(new java.awt.Color(28, 31, 44));
        cbkelas.setForeground(new java.awt.Color(255, 255, 255));
        cbkelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih" }));
        jPanel2.add(cbkelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 144, -1));

        jLabel12.setText("Total Pembayaran");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, -1, 20));

        tjumlahbulan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tjumlahbulanKeyTyped(evt);
            }
        });
        jPanel2.add(tjumlahbulan, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 270, 30, -1));
        jPanel2.add(tket, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 340, 230, -1));

        jLabel13.setText("Jumlah Bulan");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, 20));

        jLabel14.setText("Keterangan");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, 20));
        jPanel2.add(ttotalbayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 400, 160, -1));

        btHitung.setBackground(new java.awt.Color(28, 31, 44));
        btHitung.setForeground(new java.awt.Color(255, 255, 255));
        btHitung.setText("Hitung");
        btHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHitungActionPerformed(evt);
            }
        });
        jPanel2.add(btHitung, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 400, -1, -1));

        cbxKet.setText("Transfer");
        cbxKet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxKetActionPerformed(evt);
            }
        });
        jPanel2.add(cbxKet, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 310, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 414, 520));

        tabelpembayaran.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelpembayaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelpembayaranMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelpembayaran);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 150, 790, 228));

        jLabel2.setText("ID Catat");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 60, 50, 20));
        jPanel1.add(tId, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, 170, -1));

        btSimpan.setBackground(new java.awt.Color(28, 31, 44));
        btSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-checkmark-25.png"))); // NOI18N
        btSimpan.setText("Selesai & Simpan");
        btSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSimpanActionPerformed(evt);
            }
        });
        jPanel1.add(btSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 470, 170, 31));
        jPanel1.add(tno, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 276, 10, 10));

        jLabel15.setText("Masukkan ID");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 80, 20));

        tcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tcariActionPerformed(evt);
            }
        });
        jPanel1.add(tcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 100, 170, -1));

        btcari.setBackground(new java.awt.Color(28, 31, 44));
        btcari.setForeground(new java.awt.Color(255, 255, 255));
        btcari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loupe.png"))); // NOI18N
        btcari.setText("Cari");
        btcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcariActionPerformed(evt);
            }
        });
        jPanel1.add(btcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 100, 120, 32));

        btcetak.setBackground(new java.awt.Color(28, 31, 44));
        btcetak.setForeground(new java.awt.Color(255, 255, 255));
        btcetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/printer.png"))); // NOI18N
        btcetak.setText("Cetak Bukti Pembayaran");
        btcetak.setBorder(null);
        btcetak.setPreferredSize(new java.awt.Dimension(113, 23));
        btcetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcetakActionPerformed(evt);
            }
        });
        jPanel1.add(btcetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 100, 197, 32));

        btrefresh.setBackground(new java.awt.Color(28, 31, 44));
        btrefresh.setForeground(new java.awt.Color(255, 255, 255));
        btrefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        btrefresh.setText("Refresh");
        btrefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btrefreshActionPerformed(evt);
            }
        });
        jPanel1.add(btrefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 480, 160, 32));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Rp");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 400, -1, -1));

        lbJum.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(lbJum, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 400, 158, 25));

        bttotal.setBackground(new java.awt.Color(28, 31, 44));
        bttotal.setForeground(new java.awt.Color(255, 255, 255));
        bttotal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-calculator-30.png"))); // NOI18N
        bttotal.setText("Hitung Total");
        bttotal.setBorder(null);
        bttotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttotalActionPerformed(evt);
            }
        });
        jPanel1.add(bttotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 440, 160, 32));

        tBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tBayarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tBayarKeyTyped(evt);
            }
        });
        jPanel1.add(tBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 400, 170, -1));

        jLabel8.setText("Rp");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 400, -1, -1));

        jLabel10.setText("Bayar");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 400, -1, -1));

        jLabel16.setText("Kembalian");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 440, -1, 20));

        jLabel17.setText("Rp");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 440, -1, 20));
        jPanel1.add(tKembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 440, 170, -1));

        btDataBaru.setBackground(new java.awt.Color(28, 31, 44));
        btDataBaru.setForeground(new java.awt.Color(255, 255, 255));
        btDataBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-plus-25.png"))); // NOI18N
        btDataBaru.setText("Data Baru");
        btDataBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDataBaruActionPerformed(evt);
            }
        });
        jPanel1.add(btDataBaru, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 50, 120, 32));

        btKembali.setBackground(new java.awt.Color(28, 31, 44));
        btKembali.setForeground(new java.awt.Color(255, 255, 255));
        btKembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-back-25.png"))); // NOI18N
        btKembali.setText("Kembali");
        btKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btKembaliActionPerformed(evt);
            }
        });
        jPanel1.add(btKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 510, 170, 32));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1323, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTambahActionPerformed
        // TODO add your handling code here:
        if(tId.getText().equals("") || cbkelas.getSelectedItem().equals("") || cbpembayaran.getSelectedItem().equals("")|| tnis.getText().equals("")||
            tnama.getText().equals("")|| ttotal.getText().equals("")){
            JOptionPane.showMessageDialog(null, "LENGKAPI DATA !", "SDIT Bahrul Fikri", JOptionPane.WARNING_MESSAGE);

        }else{
            btSimpan.setEnabled(true);

            String nis = tnis.getText();
            String nama = tnama.getText();
            String kelas = cbkelas.getSelectedItem().toString();
            String bayar = cbpembayaran.getSelectedItem().toString();
            String cara=null;
             if(rbangsuran.isSelected()){
                cara="Angsuran";
            }else if(rbbulanan.isSelected()){
                cara="Bulanan";
            }else if(rbsekali.isSelected()){
                cara="1x Bayar";
            }
             
            String jumlah = tjumlahbulan.getText();
            String ket = tket.getText();
            String subtotal = ttotalbayar.getText();

            String id = tId.getText();
            
             long millis=System.currentTimeMillis();
                java.sql.Date date=new java.sql.Date(millis);
                String tgl = date.toString();

            String sql = "INSERT INTO tmp_pembayaran VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try {

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, null);
                    ps.setString(2, tgl);
                    ps.setString(3, id);
                    ps.setString(4, nis);
                    ps.setString(5, nama);
                    ps.setString(6, kelas);
                    ps.setString(7, bayar);
                    ps.setString(8, cara);
                    ps.setString(9, jumlah);
                    ps.setString(10, ket);
                    ps.setString(11, subtotal);
                    ps.setString(12, "0");
                    ps.setString(13, "0");
                    
                    System.out.println(ps.toString());
                    ps.executeUpdate();       
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Pembayaran Gagal");
                e.printStackTrace();
            } finally {
                idcatat();
                kosong();
                loadData();
            }
        }
    }//GEN-LAST:event_btTambahActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        // TODO add your handling code here:
        if(tId.getText().equals("") || cbkelas.getSelectedItem().equals("") || cbpembayaran.getSelectedItem().equals("")|| tnis.getText().equals("")||
            tnama.getText().equals("")|| ttotal.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Lengkapi Data!", "SDIT Bahrul Fikri", JOptionPane.WARNING_MESSAGE);
        }else{
            String cara=null;
             if(rbangsuran.isSelected()){
                cara="Angsuran";
            }else if(rbbulanan.isSelected()){
                cara="Bulanan";
            }else if(rbsekali.isSelected()){
                cara="1x Bayar";
            }            
            try {
                String sql = "UPDATE tmp_pembayaran SET nis=?, namasiswa=?, kelas=?, namabayar=?, carapembayaran=?, jumlahbulan=?, keterangan=?, total=? WHERE id_tmp='" + tno.getText()+ "'";

                try (PreparedStatement p = conn.prepareStatement(sql)) {
                    p.setString(1, tnis.getText());
                    p.setString(2, tnama.getText());
                    p.setString(3, cbkelas.getSelectedItem().toString());
                    p.setString(4, cbpembayaran.getSelectedItem().toString());
                    p.setString(5, cara);
                    p.setString(6, tjumlahbulan.getText());
                    p.setString(7, tket.getText());
                    p.setString(8, ttotalbayar.getText());
                                    
                    p.executeUpdate();
                }
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah", "SDIT Bahrul Fikri", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                System.out.println(e.getErrorCode());
            } finally {
                loadData();
                kosong();
                btSimpan.setEnabled(true);
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah", "SDIT Bahrul Fikri", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btEditActionPerformed

    private void btHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHapusActionPerformed
        // TODO add your handling code here:
       if(tId.getText().equals("") || cbkelas.getSelectedItem().equals("") || cbpembayaran.getSelectedItem().equals("")|| tnis.getText().equals("")||
            tnama.getText().equals("")|| ttotal.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Lengkapi Data!", "SDIT Bahrul Fikri", JOptionPane.WARNING_MESSAGE);
        }else{
            int confirm = JOptionPane.showConfirmDialog(null, "Ingin Menghapus Data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

            if(confirm==JOptionPane.YES_OPTION) {

                try {
                    String sql ="DELETE FROM tmp_pembayaran WHERE id_tmp='"+tno.getText()+"'";

                    java.sql.PreparedStatement ps=conn.prepareStatement(sql);
                    ps.execute();
                    JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus");
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
                loadData();
            }
        }
    }//GEN-LAST:event_btHapusActionPerformed

    private void tabelpembayaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelpembayaranMouseClicked
        // TODO add your handling code here:
        btSimpan.setEnabled(false);
        btEdit.setEnabled(true);
        btHapus.setEnabled(true);
        try{
        int bar = tabelpembayaran.getSelectedRow();
         String a = model.getValueAt(bar, 0).toString();
         String b = model.getValueAt(bar, 1).toString();
         String c = model.getValueAt(bar, 2).toString();
         String d = model.getValueAt(bar, 3).toString(); 
         String e = model.getValueAt(bar, 4).toString(); 
         String f = model.getValueAt(bar, 5).toString(); 
         String g = model.getValueAt(bar, 6).toString();
         String h = model.getValueAt(bar, 7).toString(); 

     tno.setText(a);
     tcari.setText(b);
     tnis.setText(c);
     tnama.setText(d);
     cbkelas.setSelectedItem(e);
     cbpembayaran.setSelectedItem(f);
     tket.setText(g);
     ttotalbayar.setText(h);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(this, "ERROR : \n" + e.toString(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tabelpembayaranMouseClicked

    private void tnisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnisActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "SELECT namasiswa, namakelas FROM siswa WHERE nis ='"+tnis.getText()+"'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                tnama.setText(rs.getString("namasiswa"));
                cbkelas.setSelectedItem(rs.getString("namakelas"));
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"GAGAL");
        }
    }//GEN-LAST:event_tnisActionPerformed

    private void rbangsuranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbangsuranActionPerformed
        // TODO add your handling code here:
        rbbulanan.setSelected(false);
        rbsekali.setSelected(false);
        btHitung.setEnabled(false);

        tjumlahbulan.setEnabled(false);
        tjumlahbulan.setText("0");
        
        tket.setText("-");
        
        try {           
            Statement st = conn.createStatement();
            String sql = "SELECT jumlahbayar FROM jenispembayaran WHERE namapembayaran = '"
                + cbpembayaran.getSelectedItem()+"'";
            ResultSet r = st.executeQuery(sql);

            while (r.next()) {
                ttotal.setText(r.getString("jumlahbayar"));   
            }

            r.last();
            r.first();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }//GEN-LAST:event_rbangsuranActionPerformed

    private void rbbulananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbbulananActionPerformed
        // TODO add your handling code here:
        rbangsuran.setSelected(false);
        rbsekali.setSelected(false);
        btHitung.setEnabled(true);
        tjumlahbulan.setEnabled(true);
        tket.setEnabled(true);
        
         try {           
            Statement st = conn.createStatement();
            String sql = "SELECT perbulan FROM jenispembayaran WHERE namapembayaran = '"
                + cbpembayaran.getSelectedItem()+"'";
            ResultSet r = st.executeQuery(sql);

            while (r.next()) {
                ttotal.setText(r.getString("perbulan"));                
            }

            r.last();
            r.first();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_rbbulananActionPerformed

    private void rbsekaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbsekaliActionPerformed
        // TODO add your handling code here:
        rbangsuran.setSelected(false);
        rbbulanan.setSelected(false);
        btHitung.setEnabled(false);
        tjumlahbulan.setEnabled(false);
        tjumlahbulan.setText("0");
        
        tket.setText("-");
        
         try {           
            Statement st = conn.createStatement();
            String sql = "SELECT jumlahbayar FROM jenispembayaran WHERE namapembayaran = '"
                + cbpembayaran.getSelectedItem()+"'";
            ResultSet r = st.executeQuery(sql);

            while (r.next()) {
                ttotal.setText(r.getString("jumlahbayar"));   
                ttotalbayar.setText(r.getString("jumlahbayar"));
            }

            r.last();
            r.first();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_rbsekaliActionPerformed

    private void btSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSimpanActionPerformed
        // TODO add your handling code here:
        if(tBayar.getText().equals("") ||lbJum.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Lengkapi Data!", "SDIT Bahrul Fikri", JOptionPane.WARNING_MESSAGE);
        
        }else{
            String a = tKembalian.getText();
            int ab = Integer.parseInt(String.valueOf(tKembalian.getText()));
              if(ab < 0){
                JOptionPane.showMessageDialog(null, "Maaf, Uang Anda Kurang", "SDIT Bahrul Fikri", JOptionPane.INFORMATION_MESSAGE);
                tBayar.setText("");
                tKembalian.setText("");
              }else{
         try {
                String sql = "UPDATE tmp_pembayaran SET bayar=?, kembalian=? WHERE id_catat='" + tId.getText()+ "'";

             try (PreparedStatement p = conn.prepareStatement(sql)) {
                 p.setString(1, tBayar.getText());
                 p.setString(2, tKembalian.getText());
                 p.executeUpdate();
             }
                JOptionPane.showMessageDialog(null, "Pembayaran Sukses!", "SDIT Bahrul Fikri", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getErrorCode());
            }
            try {
            Connection c = connection.getConn();
                try (Statement s = c.createStatement()) {
                    String sql = "SELECT * FROM tmp_pembayaran";
                try (ResultSet r = s.executeQuery(sql)) {
                    while (r.next()) {
                        String tgl = r.getString("tanggal");
                        String id = r.getString("id_catat");
                        String nis = r.getString("nis");
                        String nama = r.getString("namasiswa");
                        String kls = r.getString("kelas");
                        String bayar = r.getString("namabayar");
                        String cara = r.getString("carapembayaran");
                        String jumlah = r.getString("jumlahbulan");
                        String ket = r.getString("keterangan");
                        String subtotal = r.getString("subtotal");
                        String byr = r.getString("bayar");
                        String kmbl = r.getString("kembalian");
                        
                                               
                        String sqla = "INSERT INTO pembayaran VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement ps = c.prepareStatement(sqla)) {
                            ps.setString(1, null);
                            ps.setString(2, tgl);
                            ps.setString(3, id);
                            ps.setString(4, nis);
                            ps.setString(5, nama);
                            ps.setString(6, kls);
                            ps.setString(7, bayar);
                            ps.setString(8, cara);
                            ps.setString(9, jumlah);
                            ps.setString(10, ket);
                            ps.setString(11, subtotal);
                            ps.setString(12, lbJum.getText());
                            ps.setString(13, byr);
                            ps.setString(14, kmbl);
                            
                            System.out.println(ps.toString());
                                                       
                            ps.executeUpdate();
                            tcari.setText(id);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                    btSimpan.setEnabled(false);
                }
                }           
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan");
            }
         }
        }
    }//GEN-LAST:event_btSimpanActionPerformed

    private void btBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBatalActionPerformed
        // TODO add your handling code here:
        kosong();
        btSimpan.setEnabled(true);
        btEdit.setEnabled(false);
    }//GEN-LAST:event_btBatalActionPerformed

    private void btHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHitungActionPerformed
        // TODO add your handling code here:
       if(tId.getText().equals("") || cbkelas.getSelectedItem().equals("") || cbpembayaran.getSelectedItem().equals("")|| tnis.getText().equals("")||
            tnama.getText().equals("")|| ttotal.getText().equals("")){
            JOptionPane.showMessageDialog(null, "LENGKAPI DATA !", "SDIT Bahrul Fikri", JOptionPane.WARNING_MESSAGE);
        } else if (tjumlahbulan.getText().equals("")|| tket.getText().equals("")) {
                int jumlah, harga, total;

                jumlah = Integer.parseInt(ttotal.getText());

                ttotalbayar.setText(Integer.toString(jumlah));
        } else {
                int jumlah, harga, total;

                jumlah = Integer.parseInt(ttotal.getText());
                harga = Integer.parseInt(tjumlahbulan.getText());
                total = jumlah * harga;

                ttotalbayar.setText(Integer.toString(total));
        }
    }//GEN-LAST:event_btHitungActionPerformed

    private void tjumlahbulanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tjumlahbulanKeyTyped
        // TODO add your handling code here:
        FilterAngka(evt);
    }//GEN-LAST:event_tjumlahbulanKeyTyped

    private void tcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcariActionPerformed
        caridata();
    }//GEN-LAST:event_tcariActionPerformed

    private void btcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcariActionPerformed
        caridata();
    }//GEN-LAST:event_btcariActionPerformed

    private void btcetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcetakActionPerformed
        cetak(); 
    }//GEN-LAST:event_btcetakActionPerformed

    private void btrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btrefreshActionPerformed
        loadData();
        kosong();
    }//GEN-LAST:event_btrefreshActionPerformed

    private void bttotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttotalActionPerformed
        try {
            Connection c = connection.getConn();
            try (Statement s = c.createStatement()) {
                String sql = "SELECT SUM(`subtotal`) AS stotal FROM tmp_pembayaran WHERE id_catat = '"+tId.getText()+"'";
                try (ResultSet r = s.executeQuery(sql)) {
                    while (r.next()) {
                        lbJum.setText(r.getString(""+"stotal"));
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_bttotalActionPerformed

    private void tBayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tBayarKeyReleased
        bayar = Integer.parseInt(String.valueOf(tBayar.getText()));
        total = Integer.parseInt(String.valueOf(lbJum.getText()));
        kembali = bayar - total;

        tKembalian.setText(Long.toString(kembali));
    }//GEN-LAST:event_tBayarKeyReleased

    private void tBayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tBayarKeyTyped
        FilterAngka(evt);
    }//GEN-LAST:event_tBayarKeyTyped

    private void cbxKetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxKetActionPerformed
        String keterangan;
        if (cbxKet.isSelected()) {
            keterangan = "ya";
            tBayar.setText("0");
            tKembalian.setText("0");
        } else {
            keterangan = "tidak";
        }
    }//GEN-LAST:event_cbxKetActionPerformed

    private void btDataBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDataBaruActionPerformed
        try {
                String sqla ="TRUNCATE tmp_pembayaran";
                java.sql.Connection connn=(Connection)connection.getConn();
                java.sql.PreparedStatement pst=connn.prepareStatement(sqla);
                pst.execute();
                loadData();
                tcari.setText("");
                idcatat();
                kosong();
                JOptionPane.showMessageDialog(null, "Transaksi Selesai", "SDIT Bahrul Fikri", JOptionPane.INFORMATION_MESSAGE);              
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
    }//GEN-LAST:event_btDataBaruActionPerformed

    private void btKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btKembaliActionPerformed
        new Utama().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btKembaliActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Pembayaran().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBatal;
    private javax.swing.JButton btDataBaru;
    private javax.swing.JButton btEdit;
    private javax.swing.JButton btHapus;
    private javax.swing.JButton btHitung;
    private javax.swing.JButton btKembali;
    private javax.swing.JButton btSimpan;
    private javax.swing.JButton btTambah;
    private javax.swing.JButton btcari;
    private javax.swing.JButton btcetak;
    private javax.swing.JButton btrefresh;
    private javax.swing.JButton bttotal;
    private javax.swing.JComboBox<String> cbkelas;
    private javax.swing.JComboBox<String> cbpembayaran;
    private javax.swing.JCheckBox cbxKet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbJum;
    private javax.swing.JRadioButton rbangsuran;
    private javax.swing.JRadioButton rbbulanan;
    private javax.swing.ButtonGroup rbcarabayar;
    private javax.swing.JRadioButton rbsekali;
    private javax.swing.JTextField tBayar;
    private javax.swing.JTextField tId;
    private javax.swing.JTextField tKembalian;
    private javax.swing.JTable tabelpembayaran;
    private javax.swing.JTextField tcari;
    private javax.swing.JTextField tjumlahbulan;
    private javax.swing.JTextField tket;
    private javax.swing.JTextField tnama;
    private javax.swing.JTextField tnis;
    private javax.swing.JLabel tno;
    private javax.swing.JTextField ttotal;
    private javax.swing.JTextField ttotalbayar;
    // End of variables declaration//GEN-END:variables
}
