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
public class DataSuratMenyurat extends javax.swing.JFrame {
    private Connection conn = connection.getConn();
    private ResultSet hasil;
    private Statement stat;
    private String tgl;
    private String tgl2;
           
     DefaultTableModel tbl1 = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    DefaultTableModel tbl2 = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    /**
     * Creates new form suratmenyurat
     */
    public DataSuratMenyurat() {
        initComponents();
        
        suratmasuk();
        suratkeluar();
        noagendamasuk();
        noagendakeluar();
    }
    
    public void FilterAngka(KeyEvent a){
       if(Character.isAlphabetic(a.getKeyChar())){
           a.consume();
           JOptionPane.showMessageDialog(null, "Masukan Angka Saja!", "Peringatan", JOptionPane.WARNING_MESSAGE);
       }
   }
    
    private void suratmasuk() {
        Object []baris = {"No.Agenda","Tanggal Surat", "No. Surat", "Pengirim", "Tanggal Terima", "Ditujukan kepada", "Isi/Perihal", "Lampiran", "Keterangan"};
        tbl1 = new DefaultTableModel(null, baris) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelsuratmasuk.setModel(tbl1);
 
        btsave.setEnabled(true);
        btedit.setEnabled(true);
        btdelete.setEnabled(false);             
        try {
            String sql = "SELECT * FROM suratmasuk";
            stat = conn.createStatement();
            hasil = stat.executeQuery(sql);
            
            while (hasil.next()) {
                String noagenda = hasil.getString("noagenda");
                String tanggalsurat = hasil.getString("tgl_surat");
                String nosurat = hasil.getString("nosurat");
                String pengirim = hasil.getString("pengirim");
                String tglterima = hasil.getString("tgl_terima");
                String kpd = hasil.getString("kpd");
                String isi = hasil.getString("isi");
                String lamp = hasil.getString("lampiran");
                String ket = hasil.getString("ket");
                
                String[] data = { noagenda, tanggalsurat, nosurat, pengirim, tglterima, kpd, isi, lamp, ket};
                tbl1.addRow(data);
            }
        } catch (SQLException e) {
            System.out.println("Terjadi Error");
        }
    }
    
    private void suratkeluar(){
        Object []baris = {"No.Agenda","Tanggal Surat", "No. Surat", "Pengirim" , "Ditujukan kepada", "Isi/Perihal", "Lampiran", "Keterangan"};
        tbl2 = new DefaultTableModel(null, baris) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelsuratkeluar.setModel(tbl2);
 
        btsave1.setEnabled(true);
        btedit1.setEnabled(true);
        btdelete1.setEnabled(false);             
        try {
            String sql = "SELECT * FROM suratkeluar";
            stat = conn.createStatement();
            hasil = stat.executeQuery(sql);
            
            while (hasil.next()) {
                String noagenda = hasil.getString("noagenda");
                String tanggalsurat2 = hasil.getString("tgl_surat");
                String nosurat = hasil.getString("nosurat");
                String pengirim = hasil.getString("pengirim");
                String kpd = hasil.getString("kpd");
                String isi = hasil.getString("isi");
                String lamp = hasil.getString("lampiran");
                String ket = hasil.getString("ket");
                
                String[] data = { noagenda, tanggalsurat2, nosurat, pengirim,  kpd, isi, lamp, ket};
                tbl2.addRow(data);
            }
        } catch (SQLException e) {
            System.out.println("Terjadi Error");
        }
    }
    
    public void caridata(){
       String cari = tcari1.getText();
        
       Object [] Baris = {"No.Agenda","Tanggal Surat", "No. Surat", "Pengirim", "Tanggal Terima Surat", "Ditujukan kepada", "Isi/Perihal", "Lampiran", "Keterangan"};
       tbl1 = new DefaultTableModel(null, Baris);
       tabelsuratmasuk.setModel(tbl1);

       
       //untuk menampilkan hasil pencarian di table
       
       try{
           String sql="SELECT * FROM suratmasuk WHERE "
                   + "noagenda like '%"+cari+"%' "
                   + "OR tgl_surat like '%"+cari+"%' "
                   + "OR nosurat like '%"+cari+"%' "
                   + "OR pengirim like '%"+cari+"%' "
                   + "OR tgl_terima like '%"+cari+"%' "
                   + "OR kpd like '%"+cari+"%' "
                   + "OR isi like '%"+cari+"%' "
                   + "OR lampiran like '%"+cari+"%' "
                   + "OR ket like '%"+cari+"%' "
                   + "order by noagenda asc";
           Statement st=conn.createStatement();
           ResultSet hsl=st.executeQuery(sql);
           while(hsl.next()){        
                String noagenda = hsl.getString("noagenda");
                String tglsurat2 = hsl.getString("tgl_surat");
                String nosurat = hsl.getString("nosurat");
                String pengirim = hsl.getString("pengirim");
                String tglterima = hsl.getString("tgl_terima");
                String kpd = hsl.getString("kpd");
                String isi = hsl.getString("isi");
                String lamp = hsl.getString("lampiran");
                String ket = hsl.getString("ket");
                
               String[] dataField={noagenda,tglsurat2, nosurat, pengirim, tglterima, kpd, isi, lamp, ket};
               tbl1.addRow(dataField);
           }
       }
       catch(SQLException ex){
           ex.printStackTrace();
           JOptionPane.showMessageDialog(null, "Data Gagal Ditemukan","SDIT Bahrul Fikri", JOptionPane.ERROR_MESSAGE);
       }
    }
    
    public void caridata2(){
       String cari = tcari2.getText();
        
       Object [] Baris = {"No.Agenda","Tanggal Surat", "No. Surat", "Pengirim" , "Ditujukan kepada", "Isi/Perihal", "Lampiran", "Keterangan"};
       tbl2 = new DefaultTableModel(null, Baris);
       tabelsuratkeluar.setModel(tbl2);

       //untuk menampilkan di table
       try{
           String sql="SELECT * FROM suratkeluar WHERE "
                   + "noagenda like '%"+cari+"%' "
                   + "OR tgl_surat like '%"+cari+"%' "
                   + "OR nosurat like '%"+cari+"%' "
                   + "OR pengirim like '%"+cari+"%' "
                   + "OR kpd like '%"+cari+"%' "
                   + "OR isi like '%"+cari+"%' "
                   + "OR lampiran like '%"+cari+"%' "
                   + "OR ket like '%"+cari+"%' "
                   + "order by noagenda asc";
           Statement st=conn.createStatement();
           ResultSet hasil=st.executeQuery(sql);
           while(hasil.next()){
               String noagenda = hasil.getString("noagenda");
               String tglsurat3 = hasil.getString("tgl_surat");
               String nosurat = hasil.getString("nosurat");
               String pengirim = hasil.getString("pengirim");
               String kpd = hasil.getString("kpd");
               String isi = hasil.getString("isi");
               String lamp = hasil.getString("lampiran");
               String ket = hasil.getString("ket");
               
               String[] dataField = {noagenda, tglsurat3, nosurat, pengirim,  kpd, isi, lamp, ket};
               tbl2.addRow(dataField);
           }
       }
       catch(SQLException e){
           e.printStackTrace();
           JOptionPane.showMessageDialog(null, "Data Gagal Ditemukan","SDIT Bahrul Fikri", JOptionPane.ERROR_MESSAGE);
       }
    }
    
    private void noagendamasuk() {
        try {
            Connection c = connection.getConn();
            Statement s = c.createStatement();

            String sql = "SELECT * FROM suratmasuk ORDER by noagenda desc";
            ResultSet r = s.executeQuery(sql);

            if (r.next()) {
                String noag = r.getString("noagenda").substring(1);
                String AN = "" + (Integer.parseInt(noag) + 1);
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
                tagenda.setText("M" + Nol + AN);
            } else {
                tagenda.setText("M0001");
            }
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     private void noagendakeluar() {
        try {
            Connection c = connection.getConn();
            Statement s = c.createStatement();

            String sql = "SELECT * FROM suratkeluar ORDER by noagenda desc";
            ResultSet r = s.executeQuery(sql);

            if (r.next()) {
                String noag = r.getString("noagenda").substring(1);
                String AN = "" + (Integer.parseInt(noag) + 1);
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
                tagenda1.setText("L" + Nol + AN);
            } else {
                tagenda1.setText("L0001");
            }
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     public void kosong() {
        tnosurat.setText("");
        tpengirim.setText("");
        tlamp.setText("");
        tisi.setText("");
        tkpd.setText("");
        tket.setText("");
        tcari1.setText("");
        tglsurat.setDate(null);
    }
     
     public void kosong2() {
        tnosurat1.setText("");
        tpengirim1.setText("");
        tlamp1.setText("");
        tisi1.setText("");
        tkpd1.setText("");
        tket1.setText("");
        tcari2.setText("");
        tglsuratkeluar.setDate(null);
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
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tglsurat = new com.toedter.calendar.JDateChooser();
        tnosurat = new javax.swing.JTextField();
        tpengirim = new javax.swing.JTextField();
        tlamp = new javax.swing.JTextField();
        btsave = new javax.swing.JButton();
        btedit = new javax.swing.JButton();
        btdelete = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        tagenda = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tisi = new javax.swing.JTextField();
        tket = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        tkpd = new javax.swing.JTextField();
        btdelete2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelsuratmasuk = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        tcari1 = new javax.swing.JTextField();
        cari1 = new javax.swing.JButton();
        btrefresh = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelsuratkeluar = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        tglsuratkeluar = new com.toedter.calendar.JDateChooser();
        tnosurat1 = new javax.swing.JTextField();
        tpengirim1 = new javax.swing.JTextField();
        tlamp1 = new javax.swing.JTextField();
        btsave1 = new javax.swing.JButton();
        btedit1 = new javax.swing.JButton();
        btdelete1 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        tagenda1 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        tisi1 = new javax.swing.JTextField();
        tket1 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        tkpd1 = new javax.swing.JTextField();
        btdelete3 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        tcari2 = new javax.swing.JTextField();
        cari2 = new javax.swing.JButton();
        btrefresh1 = new javax.swing.JButton();
        btKembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Surat Menyurat");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1108, 570));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Input Surat Masuk"));
        jPanel9.setName("Input Surat Masuk"); // NOI18N
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Isi / Perihal");
        jPanel9.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(553, 65, -1, 17));

        jLabel3.setText("Pengirim");
        jPanel9.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 141, -1, 19));

        jLabel6.setText("Keterangan");
        jPanel9.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(553, 140, 80, 21));

        jLabel2.setText("Tanggal Surat");
        jPanel9.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 65, -1, 20));

        jLabel5.setText("Lampiran");
        jPanel9.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(553, 103, -1, 17));

        tglsurat.setBackground(new java.awt.Color(255, 255, 255));
        tglsurat.setForeground(new java.awt.Color(255, 255, 255));
        tglsurat.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tglsuratPropertyChange(evt);
            }
        });
        jPanel9.add(tglsurat, new org.netbeans.lib.awtextra.AbsoluteConstraints(166, 65, 139, -1));
        jPanel9.add(tnosurat, new org.netbeans.lib.awtextra.AbsoluteConstraints(166, 103, 231, -1));
        jPanel9.add(tpengirim, new org.netbeans.lib.awtextra.AbsoluteConstraints(166, 143, 231, -1));

        tlamp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tlampKeyTyped(evt);
            }
        });
        jPanel9.add(tlamp, new org.netbeans.lib.awtextra.AbsoluteConstraints(663, 103, 78, -1));

        btsave.setBackground(new java.awt.Color(28, 31, 44));
        btsave.setForeground(new java.awt.Color(255, 255, 255));
        btsave.setText("Simpan");
        btsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsaveActionPerformed(evt);
            }
        });
        jPanel9.add(btsave, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 39, 90, -1));

        btedit.setBackground(new java.awt.Color(28, 31, 44));
        btedit.setForeground(new java.awt.Color(255, 255, 255));
        btedit.setText("Edit");
        btedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bteditActionPerformed(evt);
            }
        });
        jPanel9.add(btedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 70, 90, -1));

        btdelete.setBackground(new java.awt.Color(28, 31, 44));
        btdelete.setForeground(new java.awt.Color(255, 255, 255));
        btdelete.setText("Hapus");
        btdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btdeleteActionPerformed(evt);
            }
        });
        jPanel9.add(btdelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 104, 90, -1));

        jLabel13.setText("No. Surat");
        jPanel9.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 103, -1, 20));
        jPanel9.add(tagenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(166, 27, 231, -1));

        jLabel14.setText("No. Agenda");
        jPanel9.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 30, -1, -1));
        jPanel9.add(tisi, new org.netbeans.lib.awtextra.AbsoluteConstraints(663, 65, 231, -1));
        jPanel9.add(tket, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 140, 240, -1));

        jLabel1.setText("Ditujukan kepada");
        jPanel9.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(553, 27, -1, 20));
        jPanel9.add(tkpd, new org.netbeans.lib.awtextra.AbsoluteConstraints(663, 27, 231, -1));

        btdelete2.setBackground(new java.awt.Color(28, 31, 44));
        btdelete2.setForeground(new java.awt.Color(255, 255, 255));
        btdelete2.setText("Batal");
        btdelete2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btdelete2ActionPerformed(evt);
            }
        });
        jPanel9.add(btdelete2, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 138, 90, -1));

        tabelsuratmasuk.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelsuratmasuk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelsuratmasukMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelsuratmasuk);

        jLabel15.setText("Pencarian");

        tcari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tcari1ActionPerformed(evt);
            }
        });

        cari1.setBackground(new java.awt.Color(28, 31, 44));
        cari1.setForeground(new java.awt.Color(255, 255, 255));
        cari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loupe.png"))); // NOI18N
        cari1.setText("Cari");
        cari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cari1ActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(tcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cari1)
                        .addGap(18, 18, 18)
                        .addComponent(btrefresh))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 1140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel15))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(tcari1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cari1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btrefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(17, 17, 17)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1195, 540));

        jTabbedPane2.addTab("Surat Masuk", jPanel5);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jPanel4.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(1157, 0, -1, -1));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setPreferredSize(new java.awt.Dimension(1108, 570));

        tabelsuratkeluar.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelsuratkeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelsuratkeluarMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabelsuratkeluar);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Input Surat Keluar"));
        jPanel11.setName("Input Surat Masuk"); // NOI18N
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setText("Isi / Perihal");
        jPanel11.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(515, 68, -1, -1));

        jLabel11.setText("Pengirim");
        jPanel11.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 141, -1, 19));

        jLabel12.setText("Keterangan");
        jPanel11.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(516, 140, 80, 20));

        jLabel20.setText("Tanggal Surat");
        jPanel11.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 65, -1, 20));

        jLabel21.setText("Lampiran");
        jPanel11.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(515, 103, -1, -1));

        tglsuratkeluar.setBackground(new java.awt.Color(255, 255, 255));
        tglsuratkeluar.setForeground(new java.awt.Color(255, 255, 255));
        tglsuratkeluar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tglsuratkeluarPropertyChange(evt);
            }
        });
        jPanel11.add(tglsuratkeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(166, 65, 139, -1));
        jPanel11.add(tnosurat1, new org.netbeans.lib.awtextra.AbsoluteConstraints(166, 103, 231, -1));
        jPanel11.add(tpengirim1, new org.netbeans.lib.awtextra.AbsoluteConstraints(166, 143, 231, -1));

        tlamp1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tlamp1KeyTyped(evt);
            }
        });
        jPanel11.add(tlamp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(625, 103, 70, -1));

        btsave1.setBackground(new java.awt.Color(28, 31, 44));
        btsave1.setForeground(new java.awt.Color(255, 255, 255));
        btsave1.setText("Simpan");
        btsave1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsave1ActionPerformed(evt);
            }
        });
        jPanel11.add(btsave1, new org.netbeans.lib.awtextra.AbsoluteConstraints(975, 39, 80, -1));

        btedit1.setBackground(new java.awt.Color(28, 31, 44));
        btedit1.setForeground(new java.awt.Color(255, 255, 255));
        btedit1.setText("Edit");
        btedit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btedit1ActionPerformed(evt);
            }
        });
        jPanel11.add(btedit1, new org.netbeans.lib.awtextra.AbsoluteConstraints(975, 68, 80, -1));

        btdelete1.setBackground(new java.awt.Color(28, 31, 44));
        btdelete1.setForeground(new java.awt.Color(255, 255, 255));
        btdelete1.setText("Hapus");
        btdelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btdelete1ActionPerformed(evt);
            }
        });
        jPanel11.add(btdelete1, new org.netbeans.lib.awtextra.AbsoluteConstraints(975, 97, 80, -1));

        jLabel22.setText("No. Surat");
        jPanel11.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 106, -1, -1));
        jPanel11.add(tagenda1, new org.netbeans.lib.awtextra.AbsoluteConstraints(166, 27, 231, -1));

        jLabel23.setText("No. Agenda");
        jPanel11.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 30, -1, -1));
        jPanel11.add(tisi1, new org.netbeans.lib.awtextra.AbsoluteConstraints(625, 65, 231, -1));
        jPanel11.add(tket1, new org.netbeans.lib.awtextra.AbsoluteConstraints(625, 141, 231, -1));

        jLabel24.setText("Ditujukan kepada");
        jPanel11.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(515, 27, -1, 20));
        jPanel11.add(tkpd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(625, 27, 231, -1));

        btdelete3.setBackground(new java.awt.Color(28, 31, 44));
        btdelete3.setForeground(new java.awt.Color(255, 255, 255));
        btdelete3.setText("Batal");
        btdelete3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btdelete3ActionPerformed(evt);
            }
        });
        jPanel11.add(btdelete3, new org.netbeans.lib.awtextra.AbsoluteConstraints(975, 126, 80, -1));

        jLabel16.setText("Pencarian");

        tcari2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tcari2ActionPerformed(evt);
            }
        });

        cari2.setBackground(new java.awt.Color(28, 31, 44));
        cari2.setForeground(new java.awt.Color(255, 255, 255));
        cari2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loupe.png"))); // NOI18N
        cari2.setText("Cari");
        cari2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cari2ActionPerformed(evt);
            }
        });

        btrefresh1.setBackground(new java.awt.Color(28, 31, 44));
        btrefresh1.setForeground(new java.awt.Color(255, 255, 255));
        btrefresh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        btrefresh1.setText("Refresh");
        btrefresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btrefresh1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(tcari2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cari2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btrefresh1))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 1159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(tcari2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cari2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btrefresh1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 1205, 550));

        jTabbedPane2.addTab("Surat Keluar", jPanel4);

        btKembali.setBackground(new java.awt.Color(28, 31, 44));
        btKembali.setForeground(new java.awt.Color(255, 255, 255));
        btKembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-back-25.png"))); // NOI18N
        btKembali.setText("Kembali");
        btKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btKembaliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane2)
                .addGap(49, 49, 49))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(btKembali)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1207, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsaveActionPerformed
        // TODO add your handling code here:
        String agenda = tagenda.getText();

        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        System.out.println(date);
        String tgll = date.toString();

        String nosurat = tnosurat.getText();
        String pengirim = tpengirim.getText();
        String kpd = tkpd.getText();
        String isi = tisi.getText();
        String lamp = tlamp.getText();
        String ket = tket.getText();

        try {
            String sql = "INSERT INTO suratmasuk VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, agenda);
                ps.setString(2, tgl);
                ps.setString(3, nosurat);
                ps.setString(4, pengirim);
                ps.setString(5, tgll);
                ps.setString(6, kpd);
                ps.setString(7, isi);
                ps.setString(8, lamp);
                ps.setString(9, ket);
                
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan");
        } finally {
            noagendamasuk();
            suratmasuk();
            kosong();
        }

    }//GEN-LAST:event_btsaveActionPerformed

    private void bteditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bteditActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "UPDATE suratmasuk SET tgl_surat=?, nosurat=?, pengirim=?, kpd=?, isi=?, lampiran=?, ket=? WHERE noagenda ='" + tagenda.getText() + "'";

            try (PreparedStatement p = conn.prepareStatement(sql)) {
                p.setString(1, tgl);
                p.setString(2, tnosurat.getText());
                p.setString(3, tpengirim.getText());
                p.setString(4, tkpd.getText());
                p.setString(5, tisi.getText());
                p.setString(6, tlamp.getText());
                p.setString(7, tket.getText());
                
                p.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah", "SDIT Bahrul Fikri", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR : \n" + e.toString(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        } finally {
            suratmasuk();
            noagendamasuk();
            kosong();
            btsave.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah", "SDIT Bahrul Fikri", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_bteditActionPerformed

    @SuppressWarnings("empty-statement")
    private void tabelsuratmasukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsuratmasukMouseClicked
        // TODO add your handling code here:
        btsave.setEnabled(false);
        btedit.setEnabled(true);
        btdelete.setEnabled(true);
        int i = tabelsuratmasuk.getSelectedRow();
        if (i == -1) {
            return;
        }
        String no = (String) tbl1.getValueAt(i, 0);
        tagenda.setText(no);
        tagenda.setEnabled(false);

        String tggl = (String) tbl1.getValueAt(i, 1);
         try{
                SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
                     java.util.Date dt = frmt.parse(tggl);
                     tglsurat.setDate(dt);
                 } catch(ParseException e) {
                     JOptionPane.showMessageDialog(this, "ERROR : \n" + e.toString(), "Kesalahan", JOptionPane.ERROR_MESSAGE);;
                 }  

        String nosurat = (String) tbl1.getValueAt(i, 2);
        tnosurat.setText(nosurat);

        String pengirim = (String) tbl1.getValueAt(i, 3);
        tpengirim.setText(pengirim);

        String kpd = (String) tbl1.getValueAt(i, 5);
        tkpd.setText(kpd);

        String isi = (String) tbl1.getValueAt(i, 6);
        tisi.setText(isi);

        String lamp = (String) tbl1.getValueAt(i, 7);
        tlamp.setText(lamp);

        String ket = (String) tbl1.getValueAt(i, 8);
        tket.setText(ket);
    }//GEN-LAST:event_tabelsuratmasukMouseClicked

    private void btsave1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsave1ActionPerformed
        // TODO add your handling code here:
        String agenda = tagenda1.getText();
        String nosurat = tnosurat1.getText();
        String pengirim = tpengirim1.getText();
        String kpd = tkpd1.getText();
        String isi = tisi1.getText();
        String lamp = tlamp1.getText();
        String ket = tket1.getText();

        try {
            String sql = "INSERT INTO suratkeluar VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, agenda);
                ps.setString(2, tgl2);
                ps.setString(3, nosurat);
                ps.setString(4, pengirim);
                ps.setString(5, kpd);
                ps.setString(6, isi);
                ps.setString(7, lamp);
                ps.setString(8, ket);
                
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan");
        } finally {
            noagendakeluar();
            suratkeluar();
            kosong2();
        }
    }//GEN-LAST:event_btsave1ActionPerformed

    private void btedit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btedit1ActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "UPDATE suratkeluar SET tgl_surat=?, nosurat=?, pengirim=?, kpd=?, isi=?, lampiran=?, ket=? WHERE noagenda ='" + tagenda1.getText() + "'";

            try (PreparedStatement p = conn.prepareStatement(sql)) {
                p.setString(1, tgl2);
                p.setString(2, tnosurat1.getText());
                p.setString(3, tpengirim1.getText());
                p.setString(4, tkpd1.getText());
                p.setString(5, tisi1.getText());
                p.setString(6, tlamp1.getText());
                p.setString(7, tket1.getText());
                
                p.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah", "SDIT Bahrul Fikri", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR : \n" + e.toString(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        } finally {
            suratkeluar();
            noagendakeluar();
            kosong2();
            btsave.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah", "SDIT Bahrul Fikri", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }//GEN-LAST:event_btedit1ActionPerformed

    private void tcari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcari1ActionPerformed
        // TODO add your handling code here:
        caridata();
    }//GEN-LAST:event_tcari1ActionPerformed

    private void cari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cari1ActionPerformed
        // TODO add your handling code here:
        caridata();
    }//GEN-LAST:event_cari1ActionPerformed

    private void tcari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcari2ActionPerformed
        // TODO add your handling code here:
        caridata2();
    }//GEN-LAST:event_tcari2ActionPerformed

    private void cari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cari2ActionPerformed
        // TODO add your handling code here:
        caridata2();
    }//GEN-LAST:event_cari2ActionPerformed

    private void tglsuratPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tglsuratPropertyChange
        // TODO add your handling code here:
           if (tglsurat.getDate() != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            tgl = format.format(tglsurat.getDate());
                }
    }//GEN-LAST:event_tglsuratPropertyChange

    private void btdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btdeleteActionPerformed
        // TODO add your handling code here:
         if ( tagenda.getText().equals("") || tnosurat.getText().equals("") || tisi.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Pilih Data","SDIT Bahrul Fikri", JOptionPane.WARNING_MESSAGE);
        } else {
            int confirm = JOptionPane.showConfirmDialog(null, "Ingin Menghapus Data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

            if(confirm==JOptionPane.YES_OPTION) {

                try {
                    String sql ="DELETE FROM suratmasuk WHERE noagenda='"+tagenda.getText()+"'";

                    java.sql.PreparedStatement ps=conn.prepareStatement(sql);
                    ps.execute();
                    JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus");
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(this, "ERROR : \n" + e.toString(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
                suratmasuk();
            }
        }
    }//GEN-LAST:event_btdeleteActionPerformed

    private void tglsuratkeluarPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tglsuratkeluarPropertyChange
        // TODO add your handling code here:
           if (tglsuratkeluar.getDate() != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            tgl2 = format.format(tglsuratkeluar.getDate());
                }
    }//GEN-LAST:event_tglsuratkeluarPropertyChange

    private void btdelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btdelete1ActionPerformed
        // TODO add your handling code here:
         if ( tagenda.getText().equals("") || tnosurat.getText().equals("") || tisi.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Pilih Data","SDIT Bahrul Fikri", JOptionPane.WARNING_MESSAGE);
        } else {
            int confirm = JOptionPane.showConfirmDialog(null, "Ingin Menghapus Data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

            if(confirm==JOptionPane.YES_OPTION) {

                try {
                    String sql ="DELETE FROM suratkeluar WHERE noagenda='"+tagenda1.getText()+"'";

                    java.sql.PreparedStatement ps=conn.prepareStatement(sql);
                    ps.execute();
                    JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus");
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(this, "ERROR : \n" + e.toString(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
                suratmasuk();
            }
        }
    }//GEN-LAST:event_btdelete1ActionPerformed

    private void tabelsuratkeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsuratkeluarMouseClicked
        // TODO add your handling code here:
        btsave1.setEnabled(false);
        btedit1.setEnabled(true);
        btdelete1.setEnabled(true);
        int i = tabelsuratkeluar.getSelectedRow();
        if (i == -1) {
            return;
        }
        String no = (String) tbl2.getValueAt(i, 0);
        tagenda1.setText(no);
        tagenda1.setEnabled(false);

        String tgll = (String) tbl2.getValueAt(i, 1);
         try{
                SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
                     java.util.Date dt = frmt.parse(tgll);
                     tglsuratkeluar.setDate(dt);
                 } catch(ParseException e) {
                     JOptionPane.showMessageDialog(this, "ERROR : \n" + e.toString(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
                 }  

        String nosurat = (String) tbl2.getValueAt(i, 2);
        tnosurat1.setText(nosurat);

        String pengirim = (String) tbl2.getValueAt(i, 3);
        tpengirim1.setText(pengirim);

        String kpd = (String) tbl2.getValueAt(i, 4);
        tkpd1.setText(kpd);

        String isi = (String) tbl2.getValueAt(i, 5);
        tisi1.setText(isi);

        String lamp = (String) tbl2.getValueAt(i, 6);
        tlamp1.setText(lamp);

        String ket = (String) tbl2.getValueAt(i, 7);
        tket1.setText(ket);
    }//GEN-LAST:event_tabelsuratkeluarMouseClicked

    private void btdelete2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btdelete2ActionPerformed
        // TODO add your handling code here:
        kosong();
        btsave.setEnabled(true);
        btedit.setEnabled(false);
        noagendamasuk();
    }//GEN-LAST:event_btdelete2ActionPerformed

    private void tlampKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tlampKeyTyped
        // TODO add your handling code here:
        FilterAngka(evt);
    }//GEN-LAST:event_tlampKeyTyped

    private void btrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btrefreshActionPerformed
        // TODO add your handling code here:
        suratmasuk();
        kosong();
    }//GEN-LAST:event_btrefreshActionPerformed

    private void btrefresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btrefresh1ActionPerformed
        // TODO add your handling code here:
        suratkeluar();
        kosong2();
    }//GEN-LAST:event_btrefresh1ActionPerformed

    private void tlamp1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tlamp1KeyTyped
        // TODO add your handling code here:
        FilterAngka(evt);
    }//GEN-LAST:event_tlamp1KeyTyped

    private void btdelete3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btdelete3ActionPerformed
        // TODO add your handling code here:
        kosong2();
        btsave1.setEnabled(true);
        btedit1.setEnabled(false);
        noagendakeluar();
    }//GEN-LAST:event_btdelete3ActionPerformed

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
            java.util.logging.Logger.getLogger(DataSuratMenyurat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new DataSuratMenyurat().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btKembali;
    private javax.swing.JButton btdelete;
    private javax.swing.JButton btdelete1;
    private javax.swing.JButton btdelete2;
    private javax.swing.JButton btdelete3;
    private javax.swing.JButton btedit;
    private javax.swing.JButton btedit1;
    private javax.swing.JButton btrefresh;
    private javax.swing.JButton btrefresh1;
    private javax.swing.JButton btsave;
    private javax.swing.JButton btsave1;
    private javax.swing.JButton cari1;
    private javax.swing.JButton cari2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable tabelsuratkeluar;
    private javax.swing.JTable tabelsuratmasuk;
    private javax.swing.JTextField tagenda;
    private javax.swing.JTextField tagenda1;
    private javax.swing.JTextField tcari1;
    private javax.swing.JTextField tcari2;
    private com.toedter.calendar.JDateChooser tglsurat;
    private com.toedter.calendar.JDateChooser tglsuratkeluar;
    private javax.swing.JTextField tisi;
    private javax.swing.JTextField tisi1;
    private javax.swing.JTextField tket;
    private javax.swing.JTextField tket1;
    private javax.swing.JTextField tkpd;
    private javax.swing.JTextField tkpd1;
    private javax.swing.JTextField tlamp;
    private javax.swing.JTextField tlamp1;
    private javax.swing.JTextField tnosurat;
    private javax.swing.JTextField tnosurat1;
    private javax.swing.JTextField tpengirim;
    private javax.swing.JTextField tpengirim1;
    // End of variables declaration//GEN-END:variables
}
