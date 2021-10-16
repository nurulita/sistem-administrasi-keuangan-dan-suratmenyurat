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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class Pengeluaran extends javax.swing.JFrame {
        public Statement st;       
        Connection conn = connection.getConn();   
        private DefaultTableModel model;
        private String tgl;


    /**
     * Creates new form EditPesanan
     */
    public Pengeluaran() {
        initComponents();
        
        model = new DefaultTableModel();

        tabelpengeluaran.setModel(model);
        model.addColumn("No");
        model.addColumn("ID Pengeluaran");
        model.addColumn("Tanggal");
        model.addColumn("Keterangan");
        model.addColumn("Rincian");
        model.addColumn("Nilai");        
        model.addColumn("Jumlah");
        model.addColumn("Subtotal");
        model.addColumn("Penanggung Jawab");
        
        idpengeluaran();
        loadData();

    }
    
    public void FilterAngka(KeyEvent a){
       if(Character.isAlphabetic(a.getKeyChar())){
           a.consume();
           JOptionPane.showMessageDialog(null, "Masukan Angka Saja!", "Peringatan", JOptionPane.WARNING_MESSAGE);
       }
   }
    
    public final void loadData() {
        btSimpan.setEnabled(true);
        btClear.setEnabled(true);
        btEdit.setEnabled(false);
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
                    
            try {
                Connection c = connection.getConn();
            try (Statement s = c.createStatement()) {
                String sql = "SELECT * FROM tmp_pengeluaran";
                    try (ResultSet rs = s.executeQuery(sql)) {
                        while (rs.next()) {
                            Object[] o = new Object[9];
                            o[0] = rs.getString("id_tmp");
                            o[1] = rs.getString("id_pengeluaran");
                            o[2] = rs.getString("tanggal");
                            o[3] = rs.getString("keterangan");
                            o[4] = rs.getString("rincian");
                            o[5] = rs.getString("nilai");                            
                            o[6] = rs.getString("jumlah");
                            o[7] = rs.getString("subtotal");
                            o[8] = rs.getString("penanggung_jawab");
                                                        
                            model.addRow(o);
                        }   
                    }
            }
            idpengeluaran();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
   }
    
    
     public void caripengeluaran(){
       String cari = tcari.getText();
        //String to=jCombo.getItemAt(i).toString();
        
       Object[] baris={"No","ID Pengeluaran","Tanggal","Keterangan","Rincian","Nilai","Jumlah","Subtotal","Penanggung Jawab"};
       model = new DefaultTableModel(null, baris);
       tabelpengeluaran.setModel(model);

       //untuk menampilkan di table
       try{
           String sql="SELECT * from pengeluaran WHERE id_pengeluaran like '%"+cari+"%'"                 
                   + " OR keterangan like '%"+cari+"%' "
                   + " OR tanggal like '%"+cari+"%' "
                   + " OR penanggung_jawab like '%"+cari+"%' "
                   + "order by no_transaksi asc";
           java.sql.Statement stmt=conn.createStatement();
           java.sql.ResultSet rslt=stmt.executeQuery(sql);
           while(rslt.next()){
               String no = rslt.getString("no_transaksi");
               String id = rslt.getString("id_pengeluaran");
               String tanggal = rslt.getString("tanggal");
               String ket = rslt.getString("keterangan");
               String rincian = rslt.getString("rincian");
               String nilai = rslt.getString("nilai");
               String jumlah = rslt.getString("jumlah");
               String subtotal = rslt.getString("subtotal");
               String pj = rslt.getString("penanggung_jawab");
               
               String[] dataField={no, id, tanggal, ket, rincian, nilai, jumlah, subtotal, pj};
               model.addRow(dataField);
               tId.setText(id);
               btTambah.setEnabled(false);
               btEdit.setEnabled(false);
               btEdit.setEnabled(false);
           }
       }
       catch(SQLException e){
           JOptionPane.showMessageDialog(null, e);
       }
    }
          
    private void idpengeluaran() {
        try {
            Connection c = connection.getConn();
            Statement s = c.createStatement();

            String sql = "SELECT * FROM pengeluaran ORDER by id_pengeluaran desc";
            ResultSet r = s.executeQuery(sql);

            if (r.next()) {
                String nofak = r.getString("id_pengeluaran").substring(1);
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
                tId.setText("K" + Nol + AN);
            } else {
                tId.setText("K0001");
            }
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
      
    public void kosong() {
        tKet.setText(null);
        tJum.setText(null);
        tRincian.setText(null);
        tSubtotal.setText(null);
        tPJ.setText(null);
        tNilai.setText(null); 
        lbJum.setText(null);
        tcari.setText(null);
        tTgl.setDate(null);
    }
    
    public void aktif() {
        tId.setEnabled(false);
        tKet.setEnabled(true);
        tRincian.setEnabled(true);
        tNilai.setEnabled(true);
        tJum.setEnabled(true);
        tPJ.setEnabled(true);
        tSubtotal.setEnabled(true);        
        btSimpan.setEnabled(false); 
        btTambah.setEnabled(true);
    }
    
    public void nonaktif() {
        tKet.setEnabled(false);
        tRincian.setEnabled(false);
        tNilai.setEnabled(false);
        tJum.setEnabled(false);
        tPJ.setEnabled(false);
        tSubtotal.setEnabled(false);        
        btSimpan.setEnabled(false); 
        btEdit.setEnabled(false); 
    }
    
    public void cetak(){
     try {
            String reportName = connection.PathReport + "buktipengeluaran.jasper";
            HashMap parameter = new HashMap();
            File reportFile = new File (reportName);
            parameter.put("id",tcari.getText());
            
            JasperReport jReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
            JasperPrint jPrint = JasperFillManager.fillReport(jReport, parameter, connection.getConn());
            JasperViewer.viewReport(jPrint, false);
     } catch (JRException e) {
         JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan!", "SDIT Bahrul Fikri", JOptionPane.WARNING_MESSAGE);
         loadData();
     } 
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbID = new javax.swing.JLabel();
        tId = new javax.swing.JTextField();
        lbRp = new javax.swing.JLabel();
        lbPJ = new javax.swing.JLabel();
        tJum = new javax.swing.JTextField();
        tSub = new javax.swing.JLabel();
        tSubtotal = new javax.swing.JTextField();
        btHitung = new javax.swing.JButton();
        btTambah = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        btClear = new javax.swing.JButton();
        btBatal = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tKet = new javax.swing.JTextArea();
        tNilai = new javax.swing.JTextField();
        lbNilai = new javax.swing.JLabel();
        lbKet = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tPJ = new javax.swing.JTextField();
        lbNilai1 = new javax.swing.JLabel();
        tRincian = new javax.swing.JTextField();
        lbID1 = new javax.swing.JLabel();
        tTgl = new com.toedter.calendar.JDateChooser();
        btSimpan = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelpengeluaran = new javax.swing.JTable();
        lbJum = new javax.swing.JLabel();
        bttotal = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btcetak = new javax.swing.JButton();
        tno = new javax.swing.JLabel();
        tcari = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btrefresh = new javax.swing.JButton();
        btcari = new javax.swing.JButton();
        btDataBaru = new javax.swing.JButton();
        btKembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pengeluaran");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1190, 585));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Pengeluaran"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbID.setText("Tanggal");
        jPanel2.add(lbID, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 51, -1));

        tId.setEditable(false);
        tId.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.add(tId, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 30, 150, -1));

        lbRp.setText("Rp");
        jPanel2.add(lbRp, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 271, -1, 20));

        lbPJ.setText("Penanggung Jawab");
        jPanel2.add(lbPJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 360, 120, 20));

        tJum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tJumKeyTyped(evt);
            }
        });
        jPanel2.add(tJum, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 320, 82, -1));

        tSub.setText("Subtotal");
        jPanel2.add(tSub, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 400, 51, 20));

        tSubtotal.setEditable(false);
        jPanel2.add(tSubtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 400, 136, -1));

        btHitung.setBackground(new java.awt.Color(28, 31, 44));
        btHitung.setForeground(new java.awt.Color(255, 255, 255));
        btHitung.setText("Hitung");
        btHitung.setBorder(null);
        btHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHitungActionPerformed(evt);
            }
        });
        jPanel2.add(btHitung, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 400, 78, 25));

        btTambah.setBackground(new java.awt.Color(28, 31, 44));
        btTambah.setForeground(new java.awt.Color(255, 255, 255));
        btTambah.setText("Tambah Pengeluaran");
        btTambah.setBorder(null);
        btTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTambahActionPerformed(evt);
            }
        });
        jPanel2.add(btTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 464, 134, 30));

        btEdit.setBackground(new java.awt.Color(28, 31, 44));
        btEdit.setForeground(new java.awt.Color(255, 255, 255));
        btEdit.setText("Edit");
        btEdit.setBorder(null);
        btEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditActionPerformed(evt);
            }
        });
        jPanel2.add(btEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 467, 70, 25));

        btClear.setBackground(new java.awt.Color(28, 31, 44));
        btClear.setForeground(new java.awt.Color(255, 255, 255));
        btClear.setText("Hapus");
        btClear.setBorder(null);
        btClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btClearActionPerformed(evt);
            }
        });
        jPanel2.add(btClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(343, 467, 70, 25));

        btBatal.setBackground(new java.awt.Color(28, 31, 44));
        btBatal.setForeground(new java.awt.Color(255, 255, 255));
        btBatal.setText("Batal");
        btBatal.setBorder(null);
        btBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBatalActionPerformed(evt);
            }
        });
        jPanel2.add(btBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 467, 70, 25));

        tKet.setColumns(20);
        tKet.setRows(5);
        jScrollPane1.setViewportView(tKet);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 119, 260, 100));
        jPanel2.add(tNilai, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 271, 167, -1));

        lbNilai.setText("Rincian");
        jPanel2.add(lbNilai, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 230, 60, 20));

        lbKet.setText("Keterangan");
        jPanel2.add(lbKet, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 121, -1, -1));

        jLabel9.setText("Jumlah");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 320, 51, 20));
        jPanel2.add(tPJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 360, 262, -1));

        lbNilai1.setText("Nilai");
        jPanel2.add(lbNilai1, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 271, -1, 20));
        jPanel2.add(tRincian, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 260, -1));

        lbID1.setText("ID Pengeluaran");
        jPanel2.add(lbID1, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 36, 116, -1));

        tTgl.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tTglPropertyChange(evt);
            }
        });
        jPanel2.add(tTgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 152, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 20, 470, 550));

        btSimpan.setBackground(new java.awt.Color(28, 31, 44));
        btSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-checkmark-25.png"))); // NOI18N
        btSimpan.setText("Selesai & Simpan");
        btSimpan.setBorder(null);
        btSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSimpanActionPerformed(evt);
            }
        });
        jPanel1.add(btSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(573, 479, 160, 32));

        tabelpengeluaran.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelpengeluaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelpengeluaranMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelpengeluaran);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(534, 110, 790, 250));

        lbJum.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jPanel1.add(lbJum, new org.netbeans.lib.awtextra.AbsoluteConstraints(573, 380, 158, 41));

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
        jPanel1.add(bttotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(573, 439, 160, 32));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Rp");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(542, 391, -1, -1));

        btcetak.setBackground(new java.awt.Color(28, 31, 44));
        btcetak.setForeground(new java.awt.Color(255, 255, 255));
        btcetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/printer.png"))); // NOI18N
        btcetak.setText("Cetak Bukti Pengeluaran");
        btcetak.setBorder(null);
        btcetak.setPreferredSize(new java.awt.Dimension(113, 23));
        btcetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcetakActionPerformed(evt);
            }
        });
        jPanel1.add(btcetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 60, 193, 32));
        jPanel1.add(tno, new org.netbeans.lib.awtextra.AbsoluteConstraints(601, 269, -1, -1));

        tcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tcariActionPerformed(evt);
            }
        });
        jPanel1.add(tcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(624, 66, 170, -1));

        jLabel14.setText("Masukkan ID");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(534, 69, 80, -1));

        btrefresh.setBackground(new java.awt.Color(28, 31, 44));
        btrefresh.setForeground(new java.awt.Color(255, 255, 255));
        btrefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        btrefresh.setText("Refresh");
        btrefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btrefreshActionPerformed(evt);
            }
        });
        jPanel1.add(btrefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 420, 160, 32));

        btcari.setBackground(new java.awt.Color(28, 31, 44));
        btcari.setForeground(new java.awt.Color(255, 255, 255));
        btcari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loupe.png"))); // NOI18N
        btcari.setText("Cari");
        btcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcariActionPerformed(evt);
            }
        });
        jPanel1.add(btcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 60, 102, 32));

        btDataBaru.setBackground(new java.awt.Color(28, 31, 44));
        btDataBaru.setForeground(new java.awt.Color(255, 255, 255));
        btDataBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-plus-25.png"))); // NOI18N
        btDataBaru.setText("Data Baru");
        btDataBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDataBaruActionPerformed(evt);
            }
        });
        jPanel1.add(btDataBaru, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 380, 160, 32));

        btKembali.setBackground(new java.awt.Color(28, 31, 44));
        btKembali.setForeground(new java.awt.Color(255, 255, 255));
        btKembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-back-25.png"))); // NOI18N
        btKembali.setText("Kembali");
        btKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btKembaliActionPerformed(evt);
            }
        });
        jPanel1.add(btKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 460, 160, 32));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bttotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttotalActionPerformed
        try {
            Connection c = connection.getConn();
            try (Statement s = c.createStatement()) {
                String sql = "SELECT SUM(`subtotal`) AS stotal FROM tmp_pengeluaran WHERE id_pengeluaran = '"+tId.getText()+"'";
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

    private void tabelpengeluaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelpengeluaranMouseClicked
        btTambah.setEnabled(false);
        btEdit.setEnabled(true);
        int i = tabelpengeluaran.getSelectedRow();
        if (i == -1) {
            return;
        }
        String no = (String) model.getValueAt(i, 0);
        tno.setText(no);
        String id = (String) model.getValueAt(i, 1);
        tId.setText(id);
        
        String tanggal = (String)model.getValueAt(i, 2);
        try{
            SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dt = frmt.parse(tanggal);
            tTgl.setDate(dt);
                 } catch(ParseException ex) {
                 }
        
        String ket = (String) model.getValueAt(i, 3);        
        tKet.setText(ket);
        String rincian = (String) model.getValueAt(i, 4);
        tRincian.setText(rincian);
        String nilai = (String) model.getValueAt(i, 5);
        tNilai.setText(nilai);
        String jumlah = (String) model.getValueAt(i, 6);
        tJum.setText(jumlah);
        String subtot = (String) model.getValueAt(i, 7);
        tSubtotal.setText(subtot);
        String pj = (String) model.getValueAt(i, 8);
        tPJ.setText(pj);

        try {
            Connection c = connection.getConn();
            try (Statement s = c.createStatement()) {
                String sql = "SELECT total FROM pengeluaran WHERE id_pengeluaran = '"+tId.getText()+"'";
                    try (ResultSet rs = s.executeQuery(sql)) {
                        while (rs.next()) {
                            String total = rs.getString("total");               
                lbJum.setText(total);
                        }     
                    }   
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_tabelpengeluaranMouseClicked

    private void btSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSimpanActionPerformed
    if(tId.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Lengkapi Data!", "SDIT Bahrul Fikri", JOptionPane.WARNING_MESSAGE);
        }else{
            try {
            Connection c = connection.getConn();
                try (Statement s = c.createStatement()) {
                    String sql = "SELECT * FROM tmp_pengeluaran WHERE id_pengeluaran = '"+tId.getText()+"'";
                try (ResultSet r = s.executeQuery(sql)) {
                    while (r.next()) {
                        String id = r.getString("id_pengeluaran");
                        
                        long millis=System.currentTimeMillis();
                        java.sql.Date date=new java.sql.Date(millis);
                        String tgll = date.toString();
                   
                        String ket = r.getString("keterangan");
                        String rincian = r.getString("rincian");
                        String nilai = r.getString("nilai");
                        String jumlah = r.getString("jumlah");
                        String subtotal = r.getString("subtotal");
                        String pj = r.getString("penanggung_jawab");
                        
                        
                        String sqla = "INSERT INTO pengeluaran VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement ps = c.prepareStatement(sqla)) {
                            ps.setString(1, null);
                            ps.setString(2, id);
                            ps.setString(3, tgll);
                            ps.setString(4, ket);
                            ps.setString(5, rincian);
                            ps.setString(6, nilai);
                            ps.setString(7, jumlah);
                            ps.setString(8, subtotal);
                            ps.setString(9, lbJum.getText());
                            ps.setString(10, pj);
                                                        
                            ps.executeUpdate();
                            tcari.setText(id);
                            btSimpan.setEnabled(false);
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "Data Berhasil Tersimpan", "SDIT Bahrul Fikri", JOptionPane.INFORMATION_MESSAGE);
                }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan");
            } 
        }
    }//GEN-LAST:event_btSimpanActionPerformed

    private void btClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btClearActionPerformed
        if ( tId.getText().equals("") || tJum.getText().equals("") || tNilai.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Pilih Data","SDIT Bahrul Fikri", JOptionPane.ERROR_MESSAGE);
        } else {
            int confirm = JOptionPane.showConfirmDialog(null, "Ingin Menghapus Data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if(confirm==JOptionPane.YES_OPTION) {
                try {
                    String sql ="DELETE FROM tmp_pengeluaran WHERE id_tmp='"+tno.getText()+"'";

                    java.sql.PreparedStatement ps=conn.prepareStatement(sql);
                    ps.execute();
                    JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus");
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(null, "Maaf, Anda Tidak Dapat Menghapus Data Ini!", "SDIT Bahrul Fikri", JOptionPane.WARNING_MESSAGE);
                }
            loadData();
            kosong();
           }
        }
    }//GEN-LAST:event_btClearActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        if(tId.getText().equals("") ||tKet.getText().equals("") 
                || tSubtotal.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Lengkapi Data!", "SDIT Bahrul Fikri", JOptionPane.INFORMATION_MESSAGE);
        }else{
            try {
                String sql = "UPDATE tmp_pengeluaran SET tanggal=?, keterangan=?, rincian=?, nilai=?, jumlah=?, subtotal=?, penanggung_jawab=?"
                        + " WHERE id_tmp='" + tno.getText()+ "'";
                try (PreparedStatement p = conn.prepareStatement(sql)) {
                    p.setString(1, tgl);
                    p.setString(2, tKet.getText());
                    p.setString(3, tRincian.getText());
                    p.setString(4, tNilai.getText());
                    p.setString(5, tJum.getText());
                    p.setString(6, tSubtotal.getText());
                    p.setString(7, tPJ.getText());
                    
                    p.executeUpdate();
                }
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah", "SDIT Bahrul Fikri", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Maaf, Anda Tidak Dapat Mengubah Data Ini!", "SDIT Bahrul Fikri", JOptionPane.WARNING_MESSAGE);
            } finally {
                loadData();
                kosong();
                btSimpan.setEnabled(true);
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah", "SDIT Bahrul Fikri", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btEditActionPerformed

    private void btTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTambahActionPerformed
        if(tId.getText().equals("") || tKet.getText().equals("") || tNilai.getText().equals("")|| tJum.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Lengkapi Data!", "SDIT Bahrul Fikri", JOptionPane.WARNING_MESSAGE);

        }else{
            btSimpan.setEnabled(true);
            
            String id = tId.getText();
            
            long millis=System.currentTimeMillis();
            java.sql.Date date=new java.sql.Date(millis);
            String tgl2 = date.toString();
            
            String ket = tKet.getText();
            String rincian = tRincian.getText();
            String nilai = tNilai.getText();
            String jumlah = tJum.getText();
            String subtotal = tSubtotal.getText();           
            String pj = tPJ.getText();            

            String sql = "INSERT INTO tmp_pengeluaran VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, null);
                    ps.setString(2, id);
                    ps.setString(3, tgl2);
                    ps.setString(4, ket);
                    ps.setString(5, rincian);
                    ps.setString(6, nilai);
                    ps.setString(7, jumlah);
                    ps.setString(8, subtotal);
                    ps.setString(9, "0");
                    ps.setString(10, pj);
                    
                    ps.executeUpdate();
                }
                
                JOptionPane.showMessageDialog(null, "Data Ditambahkan", "SDIT Bahrul Fikri", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Data Gagal Ditambahkan");
            } finally {
                tKet.setText(null);
                tRincian.setText(null);
                tNilai.setText(null);
                tJum.setText(null);
                tSubtotal.setText(null);
                tPJ.setText(null);
                loadData();
            }     
        }
    }//GEN-LAST:event_btTambahActionPerformed

    private void btHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHitungActionPerformed
        if ( tId.getText().equals("") || tKet.getText().equals("") || tRincian.getText().equals("") 
            || tJum.getText().equals("") || tNilai.getText().equals("") || tPJ.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Lengkapi Pesanan!","Kedai Avicenna", JOptionPane.WARNING_MESSAGE);
        } else {
            int jumlah, nilai, subtotal;

            jumlah = Integer.parseInt(tJum.getText());
            nilai = Integer.parseInt(tNilai.getText());
            subtotal = jumlah * nilai;

            tSubtotal.setText(Integer.toString(subtotal));
        }
    }//GEN-LAST:event_btHitungActionPerformed

    private void tJumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tJumKeyTyped
        FilterAngka(evt);
    }//GEN-LAST:event_tJumKeyTyped

    private void btcetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcetakActionPerformed
        cetak();
    }//GEN-LAST:event_btcetakActionPerformed

    private void tcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcariActionPerformed
        caripengeluaran();
        nonaktif();
    }//GEN-LAST:event_tcariActionPerformed

    private void btBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBatalActionPerformed
        kosong();
    }//GEN-LAST:event_btBatalActionPerformed

    private void tTglPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tTglPropertyChange
        if (tTgl.getDate() != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            tgl = format.format(tTgl.getDate());
                }
    }//GEN-LAST:event_tTglPropertyChange

    private void btrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btrefreshActionPerformed
        // TODO add your handling code here:
        loadData();
        kosong();
        aktif();
    }//GEN-LAST:event_btrefreshActionPerformed

    private void btcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcariActionPerformed
        // TODO add your handling code here:
        caripengeluaran();
        nonaktif();
    }//GEN-LAST:event_btcariActionPerformed

    private void btDataBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDataBaruActionPerformed
        try {
             String sqla ="TRUNCATE tmp_pengeluaran";
             java.sql.Connection con=(Connection)connection.getConn();
             java.sql.PreparedStatement pst=con.prepareStatement(sqla);
             pst.execute();
             loadData();
             kosong();
             idpengeluaran();
             aktif();
        } catch (SQLException e) {
             JOptionPane.showMessageDialog(null, "Data Gagal Dimuat");         
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pengeluaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Pengeluaran().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBatal;
    private javax.swing.JButton btClear;
    private javax.swing.JButton btDataBaru;
    private javax.swing.JButton btEdit;
    private javax.swing.JButton btHitung;
    private javax.swing.JButton btKembali;
    private javax.swing.JButton btSimpan;
    private javax.swing.JButton btTambah;
    private javax.swing.JButton btcari;
    private javax.swing.JButton btcetak;
    private javax.swing.JButton btrefresh;
    private javax.swing.JButton bttotal;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbID;
    private javax.swing.JLabel lbID1;
    private javax.swing.JLabel lbJum;
    private javax.swing.JLabel lbKet;
    private javax.swing.JLabel lbNilai;
    private javax.swing.JLabel lbNilai1;
    private javax.swing.JLabel lbPJ;
    private javax.swing.JLabel lbRp;
    private javax.swing.JTextField tId;
    private javax.swing.JTextField tJum;
    private javax.swing.JTextArea tKet;
    private javax.swing.JTextField tNilai;
    private javax.swing.JTextField tPJ;
    private javax.swing.JTextField tRincian;
    private javax.swing.JLabel tSub;
    private javax.swing.JTextField tSubtotal;
    private com.toedter.calendar.JDateChooser tTgl;
    private javax.swing.JTable tabelpengeluaran;
    private javax.swing.JTextField tcari;
    private javax.swing.JLabel tno;
    // End of variables declaration//GEN-END:variables
}
