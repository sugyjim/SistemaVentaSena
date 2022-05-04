/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;
import  com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import static com.itextpdf.text.pdf.PdfName.C;
import static com.itextpdf.text.pdf.PdfName.N;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.Cliente;
import modelo.ClienteDAO;
import modelo.DetalleVenta;
import modelo.Proveedor;
import modelo.ProveedorDAO;
import modelo.Producto;
import modelo.ProductoDAO;
import modelo.Venta;
import modelo.VentaDAO;
import modelo.config;
import modelo.eventos;
import modelo.login;
import modelo.loginDAO;
import modelo.conexion;

public final class PrincipalSistema extends javax.swing.JFrame {

    Cliente cl = new Cliente();
    ClienteDAO client = new ClienteDAO();
    Proveedor pr = new Proveedor();
    ProveedorDAO prDao = new ProveedorDAO();
    Producto pro = new Producto();
    ProductoDAO prodDAO = new ProductoDAO();
    Venta v = new Venta();
    VentaDAO vDAO = new VentaDAO();
    DetalleVenta Dv = new DetalleVenta();
    int item;
    double totalPagar = 0.00;
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tpm = new DefaultTableModel();
    eventos event = new eventos();
    config conf = new config();
    login log = new login();
    loginDAO logDAO = new loginDAO();
    conexion con = new conexion();

    public PrincipalSistema() {
        initComponents();
    
     listarConfig();
     
    }

    public PrincipalSistema(login priv) {
        initComponents();
        txtidcliente.setVisible(false);

        txtidprovedor.setVisible(false);
   
      txtidconfig.setVisible(false);
         prodDAO.ConsultarProveedor(txtproveproducto);

        if (priv.getRoll().equals("ASISTENTE")) {

            ctonusuarioprincipal.setEnabled(false);
            BTONconfiguracion.setEnabled(false);

            txtVendedor.setText(priv.getUsuario());
        } else {
            txtVendedor.setText(priv.getUsuario());

        }
    
    }

    public void ListarCliente() throws SQLException {
        List<Cliente> Listacl = client.listarCliente();
        modelo = (DefaultTableModel) jTablecliente.getModel();
        
        Object[] ob = new Object[6];
        for (int i = 0; i < Listacl.size(); i++) {
            ob[0] = Listacl.get(i).getId();
            ob[1] = Listacl.get(i).getIdentificacion();
            ob[2] = Listacl.get(i).getNombre();
            ob[3] = Listacl.get(i).getTelefono();
            ob[4] = Listacl.get(i).getDireccion();
            ob[5] = Listacl.get(i).getRazonsocial();
            modelo.addRow(ob);

        }
        jTablecliente.setModel(modelo);

    }

    public void ListarProveedor() {

        List<Proveedor> Listarpr = prDao.listarProveedor();
        modelo = (DefaultTableModel) jTableproveedor.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < Listarpr.size(); i++) {
            ob[0] = Listarpr.get(i).getId();
            ob[1] = Listarpr.get(i).getRut();
            ob[2] = Listarpr.get(i).getNombre();
            ob[3] = Listarpr.get(i).getTelefono();
            ob[4] = Listarpr.get(i).getDireccion();
            ob[5] = Listarpr.get(i).getRazon();
            modelo.addRow(ob);

        }
        jTableproveedor.setModel(modelo);

    }

    public void ListarProducto() {

        List<Producto> Listarpro = prodDAO.listarproducto();
        modelo = (DefaultTableModel) tableproducto.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < Listarpro.size(); i++) {
            ob[0] = Listarpro.get(i).getId();
            ob[1] = Listarpro.get(i).getCodigo();
            ob[2] = Listarpro.get(i).getDescripcion();
            ob[3] = Listarpro.get(i).getProveedor();
            ob[4] = Listarpro.get(i).getStock();
            ob[5] = Listarpro.get(i).getPrecio();
            modelo.addRow(ob);

        }
        tableproducto.setModel(modelo);

    }

    public void listarlogin() {
        List<login> Listarlogin = logDAO.listarlogin();
        modelo = (DefaultTableModel) Tablausuario.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < Listarlogin.size(); i++) {
            ob[0] = Listarlogin.get(i).getId();
            ob[1] = Listarlogin.get(i).getIdentificacion();
            ob[2] = Listarlogin.get(i).getNombre();
            ob[3] = Listarlogin.get(i).getUsuario();
            ob[4] = Listarlogin.get(i).getPass();
            ob[5] = Listarlogin.get(i).getRoll();

            modelo.addRow(ob);

        }
        Tablausuario.setModel(modelo);

    }

    public void listarConfig() {
        conf = prodDAO.BuscarDatos();
        txtidconfig.setText("" + conf.getId());
        txtnom_empreaCONF.setText("" + conf.getNombre_empresa());
        txtrutconfig.setText("" + conf.getRut());
        txtdireccionconfig.setText("" + conf.getDireccion());
        txttelefonoconfig.setText("" + conf.getTelefono());
        txtrazonsociconfigu.setText("" + conf.getRazon());

    }

    public void limpiarTAbla() {

        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;

        }
    }

    public void ListarVenta() {

        List<Venta> Listarventa = vDAO.listarventa();
        modelo = (DefaultTableModel) Tableventa.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < Listarventa.size(); i++) {
            ob[0] = Listarventa.get(i).getId();
            ob[1] = Listarventa.get(i).getCliente();
            ob[2] = Listarventa.get(i).getVendedor();
            ob[3] = Listarventa.get(i).getTotal();
            modelo.addRow(ob);

        }
        Tableventa.setModel(modelo);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        BTONconfiguracion = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        btonproducto = new javax.swing.JButton();
        btoproveedor = new javax.swing.JButton();
        btonclienteprincipal = new javax.swing.JButton();
        ctonusuarioprincipal = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btoeliminarventa = new javax.swing.JButton();
        txtcodigoventa = new javax.swing.JTextField();
        txtstockdisponible = new javax.swing.JTextField();
        txtprecionuevaventa = new javax.swing.JTextField();
        txtdescripcionNventa = new javax.swing.JTextField();
        txtcantidadNventa = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaVentaNueva = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtidentidicacionventa = new javax.swing.JTextField();
        txtnombreclienteventa = new javax.swing.JTextField();
        BTNGENERARVENTA = new javax.swing.JButton();
        total_pagar = new javax.swing.JLabel();
        TotalApagar = new javax.swing.JLabel();
        txttelefonoclienteventa = new javax.swing.JTextField();
        txtdireccionclienteventa = new javax.swing.JTextField();
        txtrazonclienteventa = new javax.swing.JTextField();
        txtidprod = new javax.swing.JTextField();
        btnnuevoventa = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtidentificacioncliente = new javax.swing.JTextField();
        txtnombrecliente = new javax.swing.JTextField();
        txt_direccioncliente = new javax.swing.JTextField();
        txt_telefonocliente = new javax.swing.JTextField();
        txtRazoncliente = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablecliente = new javax.swing.JTable();
        btoguardarcliente = new javax.swing.JButton();
        btoeliminarcliente = new javax.swing.JButton();
        btoactualizarcliente = new javax.swing.JButton();
        btonuevocliente = new javax.swing.JButton();
        txtidcliente = new javax.swing.JTextField();
        btonbuscarcliente = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtrutprovedor = new javax.swing.JTextField();
        txtnombreproveedor = new javax.swing.JTextField();
        txttelefonoproveedor = new javax.swing.JTextField();
        txtRazonproveedor = new javax.swing.JTextField();
        txtdireccionproveedor = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableproveedor = new javax.swing.JTable();
        BTONbuscarproveedor = new javax.swing.JButton();
        btoguardaproveedor = new javax.swing.JButton();
        btoeliminarprovedor = new javax.swing.JButton();
        btonuevoproveedor = new javax.swing.JButton();
        txtidprovedor = new javax.swing.JTextField();
        BTONbuscarprove = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtdescripcionproducto = new javax.swing.JTextField();
        txtcodigoproducto = new javax.swing.JTextField();
        txtcantidadproducto = new javax.swing.JTextField();
        txtprecioproducto = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableproducto = new javax.swing.JTable();
        txtproveproducto = new javax.swing.JComboBox<>();
        btogardarpro = new javax.swing.JButton();
        btoactualiprod = new javax.swing.JButton();
        btonuevoprod = new javax.swing.JButton();
        btneliminarprod = new javax.swing.JButton();
        btoreporteprod = new javax.swing.JButton();
        txtidproducto = new javax.swing.JTextField();
        BTONbuscarproducto = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        Tableventa = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtnombreusuario = new javax.swing.JTextField();
        txtidusuario = new javax.swing.JTextField();
        txtpassusuario = new javax.swing.JTextField();
        txtusuario = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        Tablausuario = new javax.swing.JTable();
        btoguardarusu = new javax.swing.JButton();
        btoneliminarusu = new javax.swing.JButton();
        btonnuevousu = new javax.swing.JButton();
        txtrollusuario = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txtidentificacionusu = new javax.swing.JTextField();
        BTONbuscarusuario = new javax.swing.JButton();
        btonactualizarusu = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtrazonsociconfigu = new javax.swing.JTextField();
        txtrutconfig = new javax.swing.JTextField();
        txtnom_empreaCONF = new javax.swing.JTextField();
        txtdireccionconfig = new javax.swing.JTextField();
        txttelefonoconfig = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtidconfig = new javax.swing.JTextField();
        actuaalizarconfi = new javax.swing.JButton();
        lbelvendedor = new javax.swing.JLabel();
        txtVendedor = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        TXTidventa = new javax.swing.JTextField();
        salirsistema = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        BTONconfiguracion.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BTONconfiguracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/config.png"))); // NOI18N
        BTONconfiguracion.setText("CONFIGURACION");
        BTONconfiguracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTONconfiguracionActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Nventa.png"))); // NOI18N
        jButton9.setText("NUEVA VENTA");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Carrito-de-compras.png"))); // NOI18N
        jButton10.setText("VENTA");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        btonproducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btonproducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/producto.png"))); // NOI18N
        btonproducto.setText("PRODUCTO");
        btonproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btonproductoActionPerformed(evt);
            }
        });

        btoproveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btoproveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/proveedor.png"))); // NOI18N
        btoproveedor.setText("PROVEEDORES");
        btoproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btoproveedorActionPerformed(evt);
            }
        });

        btonclienteprincipal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btonclienteprincipal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Clientes.png"))); // NOI18N
        btonclienteprincipal.setText("CLIENTE");
        btonclienteprincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btonclienteprincipalActionPerformed(evt);
            }
        });

        ctonusuarioprincipal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ctonusuarioprincipal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/USUIN.png"))); // NOI18N
        ctonusuarioprincipal.setText("USUARIO");
        ctonusuarioprincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ctonusuarioprincipalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BTONconfiguracion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ctonusuarioprincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btonproducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btoproveedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btonclienteprincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btonclienteprincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btoproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btonproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(ctonusuarioprincipal)
                .addGap(33, 33, 33)
                .addComponent(BTONconfiguracion, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 580));

        jTabbedPane2.setBackground(new java.awt.Color(153, 153, 255));
        jTabbedPane2.setForeground(new java.awt.Color(0, 0, 255));

        jPanel5.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("CODIGO");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("STOCK DISPONIBLES");
        jLabel6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel6KeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("DESCRIPCION");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("PRECIO");
        jLabel4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel4KeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("CANTIDAD");

        btoeliminarventa.setBackground(new java.awt.Color(102, 102, 255));
        btoeliminarventa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btoeliminarventa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btoeliminarventa.setText("ELIMINAR");
        btoeliminarventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btoeliminarventaActionPerformed(evt);
            }
        });

        txtcodigoventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcodigoventaActionPerformed(evt);
            }
        });
        txtcodigoventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcodigoventaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcodigoventaKeyTyped(evt);
            }
        });

        txtstockdisponible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtstockdisponibleActionPerformed(evt);
            }
        });

        txtprecionuevaventa.setEditable(false);
        txtprecionuevaventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprecionuevaventaKeyTyped(evt);
            }
        });

        txtdescripcionNventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdescripcionNventaKeyTyped(evt);
            }
        });

        txtcantidadNventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcantidadNventaActionPerformed(evt);
            }
        });
        txtcantidadNventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcantidadNventaKeyPressed(evt);
            }
        });

        tablaVentaNueva.setForeground(new java.awt.Color(102, 102, 255));
        tablaVentaNueva.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DESCRIPCION", "CANTIDAD", "PRECIO", "TOTAL"
            }
        ));
        tablaVentaNueva.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaVentaNuevaMouseClicked(evt);
            }
        });
        tablaVentaNueva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaVentaNuevaKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tablaVentaNueva);
        if (tablaVentaNueva.getColumnModel().getColumnCount() > 0) {
            tablaVentaNueva.getColumnModel().getColumn(0).setPreferredWidth(30);
            tablaVentaNueva.getColumnModel().getColumn(1).setPreferredWidth(100);
            tablaVentaNueva.getColumnModel().getColumn(2).setPreferredWidth(30);
            tablaVentaNueva.getColumnModel().getColumn(3).setPreferredWidth(30);
        }

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("IDENTIFICACION CLIENTE");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("NOMBRE CLIENTE");

        txtidentidicacionventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtidentidicacionventaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtidentidicacionventaKeyTyped(evt);
            }
        });

        txtnombreclienteventa.setEditable(false);
        txtnombreclienteventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnombreclienteventaActionPerformed(evt);
            }
        });

        BTNGENERARVENTA.setBackground(new java.awt.Color(102, 102, 255));
        BTNGENERARVENTA.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BTNGENERARVENTA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/compras.png"))); // NOI18N
        BTNGENERARVENTA.setText("VENTA");
        BTNGENERARVENTA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BTNGENERARVENTAMouseClicked(evt);
            }
        });
        BTNGENERARVENTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNGENERARVENTAActionPerformed(evt);
            }
        });
        BTNGENERARVENTA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BTNGENERARVENTAKeyPressed(evt);
            }
        });

        total_pagar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        total_pagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/money_1.png"))); // NOI18N
        total_pagar.setText("TOTAL A PAGAR");

        TotalApagar.setText("...........");
        TotalApagar.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                TotalApagarAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        TotalApagar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalApagarKeyPressed(evt);
            }
        });

        txttelefonoclienteventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttelefonoclienteventaActionPerformed(evt);
            }
        });

        txtidprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidprodActionPerformed(evt);
            }
        });

        btnnuevoventa.setBackground(new java.awt.Color(102, 102, 255));
        btnnuevoventa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnnuevoventa.setText("CANCELAR");
        btnnuevoventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoventaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtidentidicacionventa, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txtnombreclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txttelefonoclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtdireccionclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtrazonclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)))
                        .addComponent(total_pagar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TotalApagar, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel1)
                                .addGap(62, 62, 62)
                                .addComponent(jLabel3))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(txtcodigoventa, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(txtdescripcionNventa, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtcantidadNventa, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtprecionuevaventa, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(txtstockdisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtidprod, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(162, 162, 162))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 786, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(btnnuevoventa, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btoeliminarventa, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(BTNGENERARVENTA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtcantidadNventa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtcodigoventa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdescripcionNventa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtidprod, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtstockdisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtprecionuevaventa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(BTNGENERARVENTA)
                        .addGap(60, 60, 60)
                        .addComponent(btnnuevoventa, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(btoeliminarventa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(total_pagar)
                            .addComponent(TotalApagar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtidentidicacionventa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtnombreclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txttelefonoclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtdireccionclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtrazonclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(186, 186, 186))
        );

        jTabbedPane2.addTab("NUEVA VENTA", jPanel5);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Identificacion:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Nombre:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Telefono:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Direccion:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Razon social:");

        txtidentificacioncliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidentificacionclienteActionPerformed(evt);
            }
        });
        txtidentificacioncliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtidentificacionclienteKeyTyped(evt);
            }
        });

        txtnombrecliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnombreclienteActionPerformed(evt);
            }
        });

        txt_direccioncliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_direccionclienteActionPerformed(evt);
            }
        });

        txt_telefonocliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_telefonoclienteActionPerformed(evt);
            }
        });

        jTablecliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "IDENTIFICACION", "NOMBRE", "TELEFONO", "DIRECCION", "RAZON SOCIAL"
            }
        ));
        jTablecliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableclienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablecliente);
        if (jTablecliente.getColumnModel().getColumnCount() > 0) {
            jTablecliente.getColumnModel().getColumn(0).setPreferredWidth(20);
            jTablecliente.getColumnModel().getColumn(1).setPreferredWidth(30);
            jTablecliente.getColumnModel().getColumn(2).setPreferredWidth(60);
            jTablecliente.getColumnModel().getColumn(3).setPreferredWidth(25);
            jTablecliente.getColumnModel().getColumn(4).setPreferredWidth(25);
            jTablecliente.getColumnModel().getColumn(5).setPreferredWidth(20);
        }

        btoguardarcliente.setBackground(new java.awt.Color(102, 102, 102));
        btoguardarcliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btoguardarcliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Clientes.png"))); // NOI18N
        btoguardarcliente.setText("GUARDAR ");
        btoguardarcliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btoguardarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btoguardarclienteActionPerformed(evt);
            }
        });

        btoeliminarcliente.setBackground(new java.awt.Color(102, 102, 102));
        btoeliminarcliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btoeliminarcliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btoeliminarcliente.setText("ELIMINAR");
        btoeliminarcliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btoeliminarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btoeliminarclienteActionPerformed(evt);
            }
        });

        btoactualizarcliente.setBackground(new java.awt.Color(102, 102, 102));
        btoactualizarcliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btoactualizarcliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar (2).png"))); // NOI18N
        btoactualizarcliente.setText("ACTUALIZAR");
        btoactualizarcliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btoactualizarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btoactualizarclienteActionPerformed(evt);
            }
        });

        btonuevocliente.setBackground(new java.awt.Color(102, 102, 102));
        btonuevocliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btonuevocliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        btonuevocliente.setText("NUEVO");
        btonuevocliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btonuevocliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btonuevoclienteActionPerformed(evt);
            }
        });

        txtidcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidclienteActionPerformed(evt);
            }
        });

        btonbuscarcliente.setBackground(new java.awt.Color(102, 102, 102));
        btonbuscarcliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btonbuscarcliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar (2).png"))); // NOI18N
        btonbuscarcliente.setText("BUSCAR");
        btonbuscarcliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btonbuscarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btonbuscarclienteActionPerformed(evt);
            }
        });
        btonbuscarcliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btonbuscarclienteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txt_telefonocliente, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel14)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel13))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGap(31, 31, 31)
                                            .addComponent(txtnombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_direccioncliente, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtRazoncliente, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btoactualizarcliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btoguardarcliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btoeliminarcliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btonuevocliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtidentificacioncliente, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtidcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                .addGap(20, 20, 20))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(btonbuscarcliente)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtidcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtidentificacioncliente, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_telefonocliente, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(txt_direccioncliente, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtRazoncliente, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btoguardarcliente)
                            .addComponent(btonuevocliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btoactualizarcliente)
                    .addComponent(btoeliminarcliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addComponent(btonbuscarcliente)
                .addContainerGap())
        );

        jTabbedPane2.addTab("CLIENTE", jPanel2);

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));
        jPanel3.setForeground(new java.awt.Color(102, 102, 255));
        jPanel3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jPanel3KeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Rut:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Nombnre:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Telefono:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Direccion:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Razon Social:");

        txtrutprovedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtrutprovedorActionPerformed(evt);
            }
        });

        txtnombreproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnombreproveedorActionPerformed(evt);
            }
        });

        jTableproveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IDPR", "RUT", "NOMBRE", "TELEFONO", "DIRECCION", "RAZON SOCIAL"
            }
        ));
        jTableproveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableproveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTableproveedor);
        if (jTableproveedor.getColumnModel().getColumnCount() > 0) {
            jTableproveedor.getColumnModel().getColumn(0).setPreferredWidth(15);
            jTableproveedor.getColumnModel().getColumn(1).setPreferredWidth(20);
            jTableproveedor.getColumnModel().getColumn(2).setPreferredWidth(60);
            jTableproveedor.getColumnModel().getColumn(3).setPreferredWidth(25);
            jTableproveedor.getColumnModel().getColumn(4).setPreferredWidth(30);
            jTableproveedor.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        BTONbuscarproveedor.setBackground(new java.awt.Color(153, 153, 153));
        BTONbuscarproveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BTONbuscarproveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar (2).png"))); // NOI18N
        BTONbuscarproveedor.setText("ACTUALIZAR");
        BTONbuscarproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTONbuscarproveedorActionPerformed(evt);
            }
        });

        btoguardaproveedor.setBackground(new java.awt.Color(153, 153, 153));
        btoguardaproveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btoguardaproveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/proveedor.png"))); // NOI18N
        btoguardaproveedor.setText("GUARDAR");
        btoguardaproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btoguardaproveedorActionPerformed(evt);
            }
        });

        btoeliminarprovedor.setBackground(new java.awt.Color(153, 153, 153));
        btoeliminarprovedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btoeliminarprovedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btoeliminarprovedor.setText("ELIMINAR");
        btoeliminarprovedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btoeliminarprovedorActionPerformed(evt);
            }
        });

        btonuevoproveedor.setBackground(new java.awt.Color(153, 153, 153));
        btonuevoproveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btonuevoproveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        btonuevoproveedor.setText("NUEVO");
        btonuevoproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btonuevoproveedorActionPerformed(evt);
            }
        });

        BTONbuscarprove.setBackground(new java.awt.Color(153, 153, 153));
        BTONbuscarprove.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BTONbuscarprove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar (2).png"))); // NOI18N
        BTONbuscarprove.setText("BUSCAR");
        BTONbuscarprove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTONbuscarproveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtdireccionproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRazonproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(319, 319, 319)
                                .addComponent(txtidprovedor, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(25, 25, 25)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtrutprovedor, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtnombreproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txttelefonoproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(BTONbuscarproveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btoguardaproveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(69, 69, 69)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btonuevoproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btoeliminarprovedor))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(BTONbuscarprove, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtidprovedor, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtrutprovedor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtnombreproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txttelefonoproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdireccionproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRazonproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(btonuevoproveedor))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btoguardaproveedor)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BTONbuscarproveedor)
                            .addComponent(btoeliminarprovedor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(BTONbuscarprove)
                .addGap(16, 16, 16))
        );

        jTabbedPane2.addTab("PROVEEDOR", jPanel3);

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Codigo:");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("Descripcion:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Cantidad:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Proveedor");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Precio:");

        txtcodigoproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcodigoproductoActionPerformed(evt);
            }
        });

        tableproducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CODIGO ", "DESCRIPCION", "PROVEEDOR", "STOCK", "PRECIO"
            }
        ));
        tableproducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableproductoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableproducto);
        if (tableproducto.getColumnModel().getColumnCount() > 0) {
            tableproducto.getColumnModel().getColumn(0).setPreferredWidth(20);
            tableproducto.getColumnModel().getColumn(1).setPreferredWidth(50);
            tableproducto.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableproducto.getColumnModel().getColumn(3).setPreferredWidth(50);
            tableproducto.getColumnModel().getColumn(4).setPreferredWidth(50);
            tableproducto.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        txtproveproducto.setEditable(true);
        txtproveproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtproveproductoActionPerformed(evt);
            }
        });

        btogardarpro.setBackground(new java.awt.Color(102, 102, 102));
        btogardarpro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btogardarpro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/producto.png"))); // NOI18N
        btogardarpro.setText("GUARDAR");
        btogardarpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btogardarproActionPerformed(evt);
            }
        });

        btoactualiprod.setBackground(new java.awt.Color(102, 102, 102));
        btoactualiprod.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btoactualiprod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar (2).png"))); // NOI18N
        btoactualiprod.setText("ACTUALIZAR");
        btoactualiprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btoactualiprodActionPerformed(evt);
            }
        });

        btonuevoprod.setBackground(new java.awt.Color(102, 102, 102));
        btonuevoprod.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btonuevoprod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        btonuevoprod.setText("NUEVO");
        btonuevoprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btonuevoprodActionPerformed(evt);
            }
        });

        btneliminarprod.setBackground(new java.awt.Color(102, 102, 102));
        btneliminarprod.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btneliminarprod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btneliminarprod.setText("ELIMINAR");
        btneliminarprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarprodActionPerformed(evt);
            }
        });

        btoreporteprod.setBackground(new java.awt.Color(102, 102, 102));
        btoreporteprod.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btoreporteprod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/excel.png"))); // NOI18N
        btoreporteprod.setText("REPORTE");
        btoreporteprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btoreporteprodActionPerformed(evt);
            }
        });

        txtidproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidproductoActionPerformed(evt);
            }
        });

        BTONbuscarproducto.setBackground(new java.awt.Color(102, 102, 102));
        BTONbuscarproducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BTONbuscarproducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar (2).png"))); // NOI18N
        BTONbuscarproducto.setText("BUSCAR");
        BTONbuscarproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTONbuscarproductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btoactualiprod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btogardarpro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BTONbuscarproducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btonuevoprod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btneliminarprod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btoreporteprod))
                        .addGap(65, 65, 65)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel25)
                    .addComponent(jLabel24)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(txtcodigoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtidproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61))
                            .addComponent(txtcantidadproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdescripcionproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtprecioproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(txtproveproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(633, 633, 633)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txtcodigoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtidproducto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txtdescripcionproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txtcantidadproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(txtprecioproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(txtproveproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btogardarpro)
                            .addComponent(btonuevoprod))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btoactualiprod)
                            .addComponent(btneliminarprod))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BTONbuscarproducto)
                            .addComponent(btoreporteprod))
                        .addGap(43, 43, 43))))
        );

        jTabbedPane2.addTab("PRODUCTO", jPanel4);

        jPanel6.setBackground(new java.awt.Color(153, 153, 153));

        Tableventa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "CLIENTE", "VENDEDOR", "TOTAL"
            }
        ));
        Tableventa.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                TableventaAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane5.setViewportView(Tableventa);
        if (Tableventa.getColumnModel().getColumnCount() > 0) {
            Tableventa.getColumnModel().getColumn(0).setPreferredWidth(20);
            Tableventa.getColumnModel().getColumn(1).setPreferredWidth(30);
            Tableventa.getColumnModel().getColumn(2).setPreferredWidth(50);
            Tableventa.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 777, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(164, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(131, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );

        jTabbedPane2.addTab("VENTA", jPanel6);

        jPanel7.setBackground(new java.awt.Color(153, 153, 153));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("Contraseña:");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("Usuario:");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("ID");

        txtnombreusuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnombreusuarioActionPerformed(evt);
            }
        });

        Tablausuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID ", "IDENTIFICACION", "NOMBRE", "USUARIO", "PASS", "ROLL"
            }
        ));
        Tablausuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablausuarioMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(Tablausuario);
        if (Tablausuario.getColumnModel().getColumnCount() > 0) {
            Tablausuario.getColumnModel().getColumn(0).setPreferredWidth(20);
            Tablausuario.getColumnModel().getColumn(1).setPreferredWidth(30);
            Tablausuario.getColumnModel().getColumn(3).setPreferredWidth(30);
            Tablausuario.getColumnModel().getColumn(4).setPreferredWidth(20);
            Tablausuario.getColumnModel().getColumn(5).setPreferredWidth(30);
        }

        btoguardarusu.setBackground(new java.awt.Color(102, 102, 102));
        btoguardarusu.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btoguardarusu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/USUIN.png"))); // NOI18N
        btoguardarusu.setText("GUARDAR");
        btoguardarusu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btoguardarusuActionPerformed(evt);
            }
        });

        btoneliminarusu.setBackground(new java.awt.Color(102, 102, 102));
        btoneliminarusu.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btoneliminarusu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btoneliminarusu.setText("ELIMINAR");
        btoneliminarusu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btoneliminarusuActionPerformed(evt);
            }
        });

        btonnuevousu.setBackground(new java.awt.Color(102, 102, 102));
        btonnuevousu.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btonnuevousu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        btonnuevousu.setText("NUEVO");
        btonnuevousu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btonnuevousuActionPerformed(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setText("ROLL");

        txtidentificacionusu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidentificacionusuActionPerformed(evt);
            }
        });

        BTONbuscarusuario.setBackground(new java.awt.Color(102, 102, 102));
        BTONbuscarusuario.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BTONbuscarusuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar (2).png"))); // NOI18N
        BTONbuscarusuario.setText("BUSCAR");
        BTONbuscarusuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTONbuscarusuarioActionPerformed(evt);
            }
        });

        btonactualizarusu.setBackground(new java.awt.Color(102, 102, 102));
        btonactualizarusu.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btonactualizarusu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar (2).png"))); // NOI18N
        btonactualizarusu.setText("ACTUALIZAR");
        btonactualizarusu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btonactualizarusuActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel38.setText("Nombre:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Identificacion:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtrollusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtpassusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtnombreusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtidentificacionusu, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtidusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btoguardarusu)
                            .addComponent(btonactualizarusu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btoneliminarusu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btonnuevousu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 607, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(BTONbuscarusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtidusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtidentificacionusu, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnombreusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtpassusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtrollusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btonnuevousu)
                            .addComponent(btoguardarusu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btoneliminarusu)
                            .addComponent(btonactualizarusu))))
                .addGap(18, 18, 18)
                .addComponent(BTONbuscarusuario)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("USUARIO", jPanel7);

        jPanel8.setBackground(new java.awt.Color(153, 153, 153));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel31.setText("DATOS DE LA EMPRESA");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("RUT");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("DIRECCION");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setText("TELEFONO");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setText("RAZON SOCIAL");

        txtdireccionconfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdireccionconfigActionPerformed(evt);
            }
        });

        txttelefonoconfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttelefonoconfigActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setText("NOMBRE EMPRESA");

        jLabel30.setText("ID");

        actuaalizarconfi.setText("ACTUALIZAR");
        actuaalizarconfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actuaalizarconfiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(txttelefonoconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addComponent(txtdireccionconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(txtrazonsociconfigu, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(80, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(255, 255, 255)
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel35)
                        .addGap(210, 210, 210))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtidconfig, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(158, 158, 158))))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(541, 541, 541)
                .addComponent(actuaalizarconfi)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel36)
                        .addGap(310, 310, 310)
                        .addComponent(jLabel32))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(txtnom_empreaCONF, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(txtrutconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtnom_empreaCONF, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtrutconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(177, 177, 177)
                                .addComponent(jLabel34))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtidconfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(9, 9, 9)))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txttelefonoconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtrazonsociconfigu, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtdireccionconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(actuaalizarconfi)
                .addGap(64, 64, 64))
        );

        jTabbedPane2.addTab("CONFIGURACION", jPanel8);

        getContentPane().add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, 970, 460));

        lbelvendedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbelvendedor.setForeground(new java.awt.Color(204, 0, 0));
        lbelvendedor.setText("VENDEDOR");
        getContentPane().add(lbelvendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 100, 30));

        txtVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVendedorActionPerformed(evt);
            }
        });
        getContentPane().add(txtVendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 170, 30));

        jLabel9.setText("ID VENTA");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 50, 80, -1));

        TXTidventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TXTidventaActionPerformed(evt);
            }
        });
        getContentPane().add(TXTidventa, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 70, 90, 30));

        salirsistema.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        salirsistema.setText("SALIR");
        salirsistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirsistemaActionPerformed(evt);
            }
        });
        getContentPane().add(salirsistema, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 50, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btoproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btoproveedorActionPerformed
        limpiarTAbla();
        ListarProveedor();
        jTabbedPane2.setSelectedIndex(2);
    }//GEN-LAST:event_btoproveedorActionPerformed

    private void btonclienteprincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btonclienteprincipalActionPerformed
        try {
            limpiarTAbla();
            ListarCliente();
            jTabbedPane2.setSelectedIndex(1);/****/
        } catch (SQLException ex) {

        }


    }//GEN-LAST:event_btonclienteprincipalActionPerformed

    private void btonproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btonproductoActionPerformed
        limpiarTAbla();
        ListarProducto();
        jTabbedPane2.setSelectedIndex(3);
    }//GEN-LAST:event_btonproductoActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(0);

    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(4);
        limpiarTAbla();
        ListarVenta();

    }//GEN-LAST:event_jButton10ActionPerformed

    private void BTONconfiguracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTONconfiguracionActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(6);
    }//GEN-LAST:event_BTONconfiguracionActionPerformed

    private void TXTidventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TXTidventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TXTidventaActionPerformed

    private void ctonusuarioprincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ctonusuarioprincipalActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(5);
        listarlogin();
    }//GEN-LAST:event_ctonusuarioprincipalActionPerformed

    private void salirsistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirsistemaActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_salirsistemaActionPerformed

    private void actuaalizarconfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actuaalizarconfiActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtnom_empreaCONF.getText()) || !"".equals(txtrutconfig.getText()) || !"".equals(txttelefonoconfig.getText()) || !"".equals(txtdireccionconfig.getText()) || !"".equals(txtrazonsociconfigu.getText())) {

            conf.setNombre_empresa(txtnom_empreaCONF.getText());
            conf.setRuc(Integer.parseInt(txtrutconfig.getText()));
            conf.setTelefono(txttelefonoconfig.getText());
            conf.setDireccion(txtdireccionconfig.getText());
            conf.setRazon(txtrazonsociconfigu.getText());

            prodDAO.ModificarDatos(conf);
            limpiarTAbla();

        } else {
            JOptionPane.showMessageDialog(null, "los campos estan vacios");

        }
    }//GEN-LAST:event_actuaalizarconfiActionPerformed

    private void txttelefonoconfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttelefonoconfigActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttelefonoconfigActionPerformed

    private void txtdireccionconfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdireccionconfigActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdireccionconfigActionPerformed

    private void btonactualizarusuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btonactualizarusuActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtidusuario.getText())) {
            JOptionPane.showMessageDialog(null, "seleccione una fila");

        } else {

            if (!"".equals(txtidentificacionusu.getText()) || !"".equals(txtnombreusuario.getText()) || !"".equals(txtusuario.getText()) || !"".equals(txtpassusuario.getText()) || !"".equals(txtrollusuario.getText())) {

                log.setIdentificacion(txtidentificacionusu.getText());
                log.setNombre(txtnombreusuario.getText());
                log.setUsuario(txtusuario.getText());
                log.setPass(txtpassusuario.getText());
                log.setRoll(txtrollusuario.getText());
                log.setId(Integer.parseInt(txtidusuario.getText()));
                logDAO.Modificarlogi(log);
                limpiarTAbla();
                listarlogin();
                limpiarlogin();
            }
        }
    }//GEN-LAST:event_btonactualizarusuActionPerformed

    private void BTONbuscarusuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTONbuscarusuarioActionPerformed

        buscarUsuario();
    }//GEN-LAST:event_BTONbuscarusuarioActionPerformed

    private void txtidentificacionusuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidentificacionusuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidentificacionusuActionPerformed

    private void btonnuevousuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btonnuevousuActionPerformed
        // TODO add your handling code here:
        limpiarlogin();
    }//GEN-LAST:event_btonnuevousuActionPerformed

    private void btoneliminarusuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btoneliminarusuActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtidusuario.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿estas seguro de eliminar ususrio?");

            if (pregunta == 0) {
                int id = Integer.parseInt(txtidusuario.getText());
                logDAO.Eliminarlogin(id);
                limpiarTAbla();
                limpiarlogin();
                listarlogin();

            }
        }
    }//GEN-LAST:event_btoneliminarusuActionPerformed

    private void btoguardarusuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btoguardarusuActionPerformed
        boolean usuar = false;

        if (!"".equals(txtidusuario.getText()) || !"".equals(txtidentificacionusu.getText()) || !"".equals(txtnombreusuario.getText()) || !"".equals(txtusuario.getText()) || !"".equals(txtpassusuario.getText()) || !"".equals(txtrollusuario.getText())) {
            for (int i = 0; i < Tablausuario.getRowCount(); i++) {

                if (Tablausuario.getValueAt(i, 3).equals(txtusuario.getText())) {

                    usuar = true;
                    JOptionPane.showMessageDialog(null, "EL usuario YA  existe");
                }

            }

            if (usuar == false) {

                try {
                    log.setIdentificacion(txtidentificacionusu.getText());
                    log.setNombre(txtnombreusuario.getText());
                    log.setUsuario(txtusuario.getText());
                    log.setPass(txtpassusuario.getText());
                    log.setRoll(txtrollusuario.getText());

                    logDAO.RegistrarU(log);
                    limpiarTAbla();
                    listarlogin();

                    JOptionPane.showMessageDialog(null, "usuario Registrado");

                } catch (Exception e) {

                    System.out.println(e.toString());
                }

            } else {

                JOptionPane.showMessageDialog(null, "campos vacios");
            }

        }
    }//GEN-LAST:event_btoguardarusuActionPerformed

    private void TablausuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablausuarioMouseClicked
        // TODO add your handling code here:
        int fila = Tablausuario.rowAtPoint(evt.getPoint());

        txtidusuario.setText(Tablausuario.getValueAt(fila, 0).toString());
        txtidentificacionusu.setText(Tablausuario.getValueAt(fila, 1).toString());
        txtnombreusuario.setText(Tablausuario.getValueAt(fila, 2).toString());
        txtusuario.setText(Tablausuario.getValueAt(fila, 3).toString());
        txtpassusuario.setText(Tablausuario.getValueAt(fila, 4).toString());
        txtrollusuario.setText(Tablausuario.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_TablausuarioMouseClicked

    private void txtnombreusuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreusuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreusuarioActionPerformed

    private void BTONbuscarproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTONbuscarproductoActionPerformed
        // TODO add your handling code here:
        buscarProducto();
    }//GEN-LAST:event_BTONbuscarproductoActionPerformed

    private void txtidproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidproductoActionPerformed

    private void btoreporteprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btoreporteprodActionPerformed
        // TODO add your handling code here:

     
    }//GEN-LAST:event_btoreporteprodActionPerformed

    private void btneliminarprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarprodActionPerformed
        if (!"".equals(txtidproducto.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿estas seguro de eliminar producto?");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtidproducto.getText());
                prodDAO.EliminarProducto(id);
                limpiarTAbla();
                limpiarProducto();
                ListarProducto();
            }
        }
    }//GEN-LAST:event_btneliminarprodActionPerformed

    private void btonuevoprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btonuevoprodActionPerformed
        // TODO add your handling code here:
        limpiarProducto();
    }//GEN-LAST:event_btonuevoprodActionPerformed

    private void btoactualiprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btoactualiprodActionPerformed
        if ("".equals(txtidproducto.getText())) {
            JOptionPane.showMessageDialog(null, "seleccione una fila");

        } else {

            if (!"".equals(txtcodigoproducto.getText()) || !"".equals(txtdescripcionproducto.getText()) || !"".equals(txtproveproducto.getSelectedItem()) || !"".equals(txtcantidadproducto.getText()) || !"".equals(txtprecioproducto.getText())) {

                pro.setCodigo(txtcodigoproducto.getText());
                pro.setDescripcion(txtdescripcionproducto.getText());
                pro.setProveedor(txtproveproducto.getSelectedItem().toString());
                pro.setStock(Integer.parseInt(txtcantidadproducto.getText()));
                pro.setPrecio(Double.parseDouble(txtprecioproducto.getText()));
                pro.setId(Integer.parseInt(txtidproducto.getText()));
                prodDAO.ModificarProducto(pro);
                JOptionPane.showMessageDialog(null, "producto actualizado");
                limpiarTAbla();
                ListarProducto();
                limpiarProducto();

            }
        }
    }//GEN-LAST:event_btoactualiprodActionPerformed

    private void btogardarproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btogardarproActionPerformed

        boolean bs=false;

        if (!"".equals(txtidprod.getText()) || !"".equals(txtdescripcionproducto.getText()) || !"".equals(txtcodigoproducto.getText()) || !"".equals(txtproveproducto.getSelectedItem()) || !"".equals(txtcantidadproducto.getText()) || !"".equals(txtprecioproducto.getText())) {

            for (int i = 0; i < tableproducto.getRowCount(); i++) {
                if (tableproducto.getValueAt(i, 1).equals(txtcodigoproducto.getText())) {

                    bs = true;
                    JOptionPane.showMessageDialog(null, "EL PRODUCTO YA ESTA REGISTRADO");

                }

            }

            if (bs == false) {
                try {
                    pro.setCodigo(txtcodigoproducto.getText());
                    pro.setDescripcion(txtdescripcionproducto.getText());
                    pro.setProveedor(txtproveproducto.getSelectedItem().toString());
                    pro.setStock(Integer.parseInt(txtcantidadproducto.getText()));
                    pro.setPrecio(Double.parseDouble(txtprecioproducto.getText()));
                    prodDAO.RegistrarProducto(pro);
                    limpiarTAbla();
                    ListarProducto();

                    JOptionPane.showMessageDialog(null, "producto Registrado");

                } catch (Exception e) {

                    System.out.println(e.toString());

                }
            } else {
                JOptionPane.showMessageDialog(null, "campos vacios");
            }
        }
    }//GEN-LAST:event_btogardarproActionPerformed

    private void txtproveproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtproveproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtproveproductoActionPerformed

    private void tableproductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableproductoMouseClicked
        int fila = tableproducto.rowAtPoint(evt.getPoint());
        txtidproducto.setText(tableproducto.getValueAt(fila, 0).toString());
        txtcodigoproducto.setText(tableproducto.getValueAt(fila, 1).toString());
        txtdescripcionproducto.setText(tableproducto.getValueAt(fila, 2).toString());
        txtproveproducto.setSelectedItem(tableproducto.getValueAt(fila, 3).toString());
        txtcantidadproducto.setText(tableproducto.getValueAt(fila, 4).toString());
        txtprecioproducto.setText(tableproducto.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_tableproductoMouseClicked

    private void txtcodigoproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodigoproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcodigoproductoActionPerformed

    private void jPanel3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel3KeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_jPanel3KeyTyped

    private void BTONbuscarproveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTONbuscarproveActionPerformed
        // TODO add your handling code here:
        buscarproveedor();
    }//GEN-LAST:event_BTONbuscarproveActionPerformed

    private void btonuevoproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btonuevoproveedorActionPerformed
        // TODO add your handling code here:
        limpiarProveedor();
    }//GEN-LAST:event_btonuevoproveedorActionPerformed

    private void btoeliminarprovedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btoeliminarprovedorActionPerformed
        if (!"".equals(txtidprovedor.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "estas seguro de eliminar a proveedor");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtidprovedor.getText());
                prDao.EliminarProveedor(id);
                limpiarTAbla();
                limpiarProveedor();
                ListarProveedor();

            }

        } else {

            JOptionPane.showMessageDialog(null, "selecciones una fila");
        }
    }//GEN-LAST:event_btoeliminarprovedorActionPerformed

    private void btoguardaproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btoguardaproveedorActionPerformed

        boolean bsd=false;

        if (!"".equals(txtrutprovedor.getText()) || !"".equals(txtnombreproveedor.getText()) || !"".equals(txttelefonoproveedor.getText()) || !"".equals(txtdireccionproveedor.getText()) || !"".equals(txtRazonproveedor.getText())) {

            for (int i = 0; i < jTableproveedor.getRowCount(); i++) {
                if (jTableproveedor.getValueAt(i, 2).equals(txtrutprovedor.getText())) {

                    bsd = true;
                    JOptionPane.showMessageDialog(null, "EL PROVEEDOR YA ESTA REGISTRADO");

                }

            }

            if (bsd == false) {

                try {
                    pr.setRut(Integer.parseInt(txtrutprovedor.getText()));
                    pr.setNombre(txtnombreproveedor.getText());
                    pr.setTelefono(txttelefonoproveedor.getText());
                    pr.setDireccion(txtdireccionproveedor.getText());
                    pr.setRazon(txtRazonproveedor.getText());
                    prDao.Registrarproveedor(pr);

                    limpiarTAbla();
                    ListarProveedor();
                    JOptionPane.showMessageDialog(null, "proveedor registrado");

                } catch (Exception e) {
                    System.out.println(e.toString());
                }

            } else {

                JOptionPane.showMessageDialog(null, "los campos estan vacios");

            }
        }
    }//GEN-LAST:event_btoguardaproveedorActionPerformed

    private void BTONbuscarproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTONbuscarproveedorActionPerformed
        if ("".equals(txtidprovedor.getText())) {
            JOptionPane.showMessageDialog(null, "seleccione una fila");

        } else {

            if (!"".equals(txtrutprovedor.getText()) || !"".equals(txtnombreproveedor.getText()) || !"".equals(txttelefonoproveedor.getText()) || !"".equals(txtdireccionproveedor.getText()) || !"".equals(txtRazonproveedor.getText())) {

                pr.setRut(Integer.parseInt(txtrutprovedor.getText()));
                pr.setNombre(txtnombreproveedor.getText());
                pr.setTelefono(txttelefonoproveedor.getText());
                pr.setDireccion(txtdireccionproveedor.getText());
                pr.setRazon(txtRazonproveedor.getText());
                pr.setId(Integer.parseInt(txtidprovedor.getText()));
                prDao.ModificarProveedor(pr);
                limpiarTAbla();
                ListarProveedor();
                limpiarProveedor();
            }
        }
    }//GEN-LAST:event_BTONbuscarproveedorActionPerformed

    private void jTableproveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableproveedorMouseClicked
        int fila = jTableproveedor.rowAtPoint(evt.getPoint());
        txtidprovedor.setText(jTableproveedor.getValueAt(fila, 0).toString());
        txtrutprovedor.setText(jTableproveedor.getValueAt(fila, 1).toString());
        txtnombreproveedor.setText(jTableproveedor.getValueAt(fila, 2).toString());
        txttelefonoproveedor.setText(jTableproveedor.getValueAt(fila, 3).toString());
        txtdireccionproveedor.setText(jTableproveedor.getValueAt(fila, 4).toString());
        txtRazonproveedor.setText(jTableproveedor.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_jTableproveedorMouseClicked

    private void txtnombreproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreproveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreproveedorActionPerformed

    private void txtrutprovedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtrutprovedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrutprovedorActionPerformed

    private void btonbuscarclienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btonbuscarclienteKeyPressed

    }//GEN-LAST:event_btonbuscarclienteKeyPressed

    private void btonbuscarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btonbuscarclienteActionPerformed

        buscarcliente();
    }//GEN-LAST:event_btonbuscarclienteActionPerformed

    private void txtidclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidclienteActionPerformed

    private void btonuevoclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btonuevoclienteActionPerformed
        limpiarCliente();
    }//GEN-LAST:event_btonuevoclienteActionPerformed

    private void btoactualizarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btoactualizarclienteActionPerformed
        if ("".equals(txtidcliente.getText())) {
            JOptionPane.showMessageDialog(null, "selecione una fila");

        } else {
            if (!"".equals(txtidentificacioncliente.getText()) || !"".equals(txtnombrecliente.getText()) || !"".equals(txt_telefonocliente.getText()) || !"".equals(txt_direccioncliente.getText()) || !"".equals(txtRazoncliente.getText()) || !"".equals(Integer.parseInt(txtidcliente.getText()))) {

                cl.setIdentificacion(txtidentificacioncliente.getText());
                cl.setNombre(txtnombrecliente.getText());
                cl.setTelefono(txt_telefonocliente.getText());
                cl.setDireccion(txt_direccioncliente.getText());
                cl.setRazonsocial(txtRazoncliente.getText());
                cl.setId(Integer.parseInt(txtidcliente.getText()));

                client.ModificarCliente(cl);
                limpiarTAbla();
                limpiarCliente();
                try {
                    ListarCliente();
                } catch (SQLException ex) {

                }

            } else {
                JOptionPane.showMessageDialog(null, "los campos estan vacios");

            }

        }
    }//GEN-LAST:event_btoactualizarclienteActionPerformed

    private void btoeliminarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btoeliminarclienteActionPerformed
        if (!"".equals(txtidcliente.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿estas seguro de eliminar cliente?");

            if (pregunta == 0) {
                int id = Integer.parseInt(txtidcliente.getText());
                client.EliminarCliente(id);
                limpiarTAbla();
                limpiarCliente();
                try {
                    ListarCliente();
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }

            }

        }
    }//GEN-LAST:event_btoeliminarclienteActionPerformed

    private void btoguardarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btoguardarclienteActionPerformed

        boolean bandera = false;

        if (!"".equals(txtidentificacioncliente.getText()) || !"".equals(txtnombrecliente.getText()) || !"".equals(txt_telefonocliente.getText()) || !"".equals(txtdireccionclienteventa.getText())) {

            for (int i = 0; i < jTablecliente.getRowCount(); i++) {
                if (jTablecliente.getValueAt(i, 1).equals(txtidentificacioncliente.getText())) {

                    bandera = true;
                    JOptionPane.showMessageDialog(null, "EL CLIENTE YA ESTA REGISTRADO");

                }

            }

            if (bandera == false) {

                try {
                    cl.setIdentificacion(txtidentificacioncliente.getText());
                    cl.setNombre(txtnombrecliente.getText());
                    cl.setTelefono(txt_telefonocliente.getText());
                    cl.setDireccion(txt_direccioncliente.getText());
                    cl.setRazonsocial(txtRazoncliente.getText());
                    client.registrarcliente(cl);

                    limpiarTAbla();
                    limpiarCliente();
                    ListarCliente();

                    JOptionPane.showMessageDialog(null, "cliente regidtrado");
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }

            } else {

                JOptionPane.showMessageDialog(null, "no se pudo registrrar ningun cliente");
            }
        } else {

        }
    }//GEN-LAST:event_btoguardarclienteActionPerformed

    /**
     * metodo para seleccionar editar y eliminar cliente
     */
    private void jTableclienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableclienteMouseClicked
        int fila = jTablecliente.rowAtPoint(evt.getPoint());
        txtidcliente.setText(jTablecliente.getValueAt(fila, 0).toString());
        txtidentificacioncliente.setText(jTablecliente.getValueAt(fila, 1).toString());
        txtnombrecliente.setText(jTablecliente.getValueAt(fila, 2).toString());
        txt_telefonocliente.setText(jTablecliente.getValueAt(fila, 3).toString());
        txt_direccioncliente.setText(jTablecliente.getValueAt(fila, 4).toString());
        txtRazoncliente.setText(jTablecliente.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_jTableclienteMouseClicked

    private void txt_telefonoclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_telefonoclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_telefonoclienteActionPerformed

    private void txt_direccionclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_direccionclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_direccionclienteActionPerformed

    private void txtnombreclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreclienteActionPerformed

    private void txtidentificacionclienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidentificacionclienteKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtidentificacionclienteKeyTyped

    private void txtidentificacionclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidentificacionclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidentificacionclienteActionPerformed

    private void btnnuevoventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoventaActionPerformed
        // TODO add your handling code here:
        LimpiarVenta();
    }//GEN-LAST:event_btnnuevoventaActionPerformed

    private void txtidprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidprodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidprodActionPerformed

    private void txttelefonoclienteventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttelefonoclienteventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttelefonoclienteventaActionPerformed

    private void TotalApagarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalApagarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalApagarKeyPressed

    private void TotalApagarAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_TotalApagarAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalApagarAncestorAdded

    private void BTNGENERARVENTAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BTNGENERARVENTAKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BTNGENERARVENTAKeyPressed

    private void BTNGENERARVENTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNGENERARVENTAActionPerformed
        // TODO add your handling code here:

        if (tablaVentaNueva.getRowCount() > 0) {
            if (!"".equals(txtnombreclienteventa.getText())) {

                RegistrarVenta();
                RegistrarDetalle();
                ActualizarStock();
                try {
                    Rpdf();
                } catch (DocumentException ex) {
                    Logger.getLogger(PrincipalSistema.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PrincipalSistema.class.getName()).log(Level.SEVERE, null, ex);
                }
                Limpiartablenventa();
                LimpiarClienteVenta();
                LimpiarVenta();

            } else {
                JOptionPane.showMessageDialog(null, "debes buscar un cliente");
            }

        } else {

            JOptionPane.showMessageDialog(null, "ingrese un producto");
        }
    }//GEN-LAST:event_BTNGENERARVENTAActionPerformed

    private void BTNGENERARVENTAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNGENERARVENTAMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BTNGENERARVENTAMouseClicked

    private void txtnombreclienteventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreclienteventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreclienteventaActionPerformed

    private void txtidentidicacionventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidentidicacionventaKeyTyped
        // TODO add your handling code here:
        event.numberDecimalKeyPress(evt, txtnombrecliente);
    }//GEN-LAST:event_txtidentidicacionventaKeyTyped

    private void txtidentidicacionventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidentidicacionventaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtidentidicacionventa.getText())) {
                String ident = txtidentidicacionventa.getText();

                cl = client.BuscarCliente(ident);
                if (cl.getNombre() != null) {
                    txtnombreclienteventa.setText("" + cl.getNombre());
                    txttelefonoclienteventa.setText("" + cl.getTelefono());
                    txtdireccionclienteventa.setText("" + cl.getDireccion());
                    txtrazonclienteventa.setText("" + cl.getRazonsocial());
                } else {
                    txtidentidicacionventa.setText("");
                    JOptionPane.showMessageDialog(null, "EL CLIENTE NO EXISTE");
                }
            }

        }
    }//GEN-LAST:event_txtidentidicacionventaKeyPressed

    private void tablaVentaNuevaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaVentaNuevaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaVentaNuevaKeyPressed

    private void tablaVentaNuevaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVentaNuevaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaVentaNuevaMouseClicked

    private void txtcantidadNventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidadNventaKeyPressed
        event.numberDecimalKeyPress(evt, txtcantidadNventa);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            if (!"".equals(txtcantidadNventa.getText())) {

                String cod = txtcodigoventa.getText();
                String desc= txtdescripcionNventa.getText();
                double cant = Double.parseDouble(txtcantidadNventa.getText());
                double precio = Double.parseDouble(txtprecionuevaventa.getText());
                double Total = cant * precio;
                double stock = Double.parseDouble(txtstockdisponible.getText());
                if (stock >= cant) {

                    item = item + 1;
                    DefaultTableModel tpm = (DefaultTableModel) tablaVentaNueva.getModel();
                    for (int i = 0; i < tablaVentaNueva.getRowCount(); i++) {
                        if (tablaVentaNueva.getValueAt(i, 1).equals(txtdescripcionNventa.getText())) {
                            JOptionPane.showMessageDialog(null, "EL PRODUCTO YA ESTA REGISTRADO");
                            return;

                        }

                    }

                    ArrayList Lista = new ArrayList();
                    Lista.add(item);
                    Lista.add(cod);
                    Lista.add(desc);
                    Lista.add(cant);
                    Lista.add(precio);
                    Lista.add(Total);
                    Object[] O = new Object[5];
                    O[0] = Lista.get(1);
                    O[1] = Lista.get(2);
                    O[2] = Lista.get(3);
                    O[3] = Lista.get(4);
                    O[4] = Lista.get(5);
                    tpm.addRow(O);

                    tablaVentaNueva.setModel(tpm);
                    TotalPagar();
                    LimpiarVenta();
                    txtcodigoventa.requestFocus();
                } else {

                    JOptionPane.showMessageDialog(null, "stock no disponible");
                    LimpiarVenta();
                }

            } else {
                JOptionPane.showMessageDialog(null, "ingrese cantidad");
            }

        }
    }//GEN-LAST:event_txtcantidadNventaKeyPressed

    private void txtcantidadNventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcantidadNventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcantidadNventaActionPerformed

    private void txtdescripcionNventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdescripcionNventaKeyTyped
        // TODO add your handling code here:
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtdescripcionNventaKeyTyped

    private void txtprecionuevaventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecionuevaventaKeyTyped
        // TODO add your handling code here:
        event.numberDecimalKeyPress(evt, txtprecionuevaventa);
    }//GEN-LAST:event_txtprecionuevaventaKeyTyped

    private void txtcodigoventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodigoventaKeyTyped
        // TODO add your handling code here:
        event.numberDecimalKeyPress(evt, txtcodigoventa);
    }//GEN-LAST:event_txtcodigoventaKeyTyped

    private void txtcodigoventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodigoventaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtcodigoventa.getText())) {
                String cod = txtcodigoventa.getText();
                pro = prodDAO.BuscarPro(cod);
                if (pro.getDescripcion() != null) {
                    txtdescripcionNventa.setText(" " + pro.getDescripcion());
                    txtprecionuevaventa.setText(" " + pro.getPrecio());
                    txtstockdisponible.setText(" " + pro.getStock());
                    txtcantidadNventa.requestFocus();

                } else {

                    LimpiarVenta();
                    txtcodigoventa.requestFocus();

                }

            } else {
                JOptionPane.showMessageDialog(null, "ingrese el codigo del producto");
                txtcodigoventa.requestFocus();
            }

        }
    }//GEN-LAST:event_txtcodigoventaKeyPressed

    private void txtcodigoventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodigoventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcodigoventaActionPerformed

    private void btoeliminarventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btoeliminarventaActionPerformed
        // TODO add your handling code here:
        modelo = (DefaultTableModel) tablaVentaNueva.getModel();
        modelo.removeRow(tablaVentaNueva.getSelectedRow());
        TotalPagar();
        txtcodigoventa.requestFocus();
    }//GEN-LAST:event_btoeliminarventaActionPerformed

    private void jLabel4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel4KeyPressed

    private void jLabel6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel6KeyPressed

    private void txtstockdisponibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtstockdisponibleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtstockdisponibleActionPerformed

    private void txtVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVendedorActionPerformed

    private void TableventaAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_TableventaAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_TableventaAncestorAdded

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
            java.util.logging.Logger.getLogger(PrincipalSistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalSistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalSistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalSistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalSistema().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTNGENERARVENTA;
    private javax.swing.JButton BTONbuscarproducto;
    private javax.swing.JButton BTONbuscarprove;
    private javax.swing.JButton BTONbuscarproveedor;
    private javax.swing.JButton BTONbuscarusuario;
    private javax.swing.JButton BTONconfiguracion;
    private javax.swing.JTextField TXTidventa;
    private javax.swing.JTable Tablausuario;
    private javax.swing.JTable Tableventa;
    private javax.swing.JLabel TotalApagar;
    private javax.swing.JButton actuaalizarconfi;
    private javax.swing.JButton btneliminarprod;
    private javax.swing.JButton btnnuevoventa;
    private javax.swing.JButton btoactualiprod;
    private javax.swing.JButton btoactualizarcliente;
    private javax.swing.JButton btoeliminarcliente;
    private javax.swing.JButton btoeliminarprovedor;
    private javax.swing.JButton btoeliminarventa;
    private javax.swing.JButton btogardarpro;
    private javax.swing.JButton btoguardaproveedor;
    private javax.swing.JButton btoguardarcliente;
    private javax.swing.JButton btoguardarusu;
    private javax.swing.JButton btonactualizarusu;
    private javax.swing.JButton btonbuscarcliente;
    private javax.swing.JButton btonclienteprincipal;
    private javax.swing.JButton btoneliminarusu;
    private javax.swing.JButton btonnuevousu;
    private javax.swing.JButton btonproducto;
    private javax.swing.JButton btonuevocliente;
    private javax.swing.JButton btonuevoprod;
    private javax.swing.JButton btonuevoproveedor;
    private javax.swing.JButton btoproveedor;
    private javax.swing.JButton btoreporteprod;
    private javax.swing.JButton ctonusuarioprincipal;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTablecliente;
    private javax.swing.JTable jTableproveedor;
    private javax.swing.JLabel lbelvendedor;
    private javax.swing.JButton salirsistema;
    private javax.swing.JTable tablaVentaNueva;
    private javax.swing.JTable tableproducto;
    private javax.swing.JLabel total_pagar;
    private javax.swing.JTextField txtRazoncliente;
    private javax.swing.JTextField txtRazonproveedor;
    private javax.swing.JTextField txtVendedor;
    private javax.swing.JTextField txt_direccioncliente;
    private javax.swing.JTextField txt_telefonocliente;
    private javax.swing.JTextField txtcantidadNventa;
    private javax.swing.JTextField txtcantidadproducto;
    private javax.swing.JTextField txtcodigoproducto;
    private javax.swing.JTextField txtcodigoventa;
    private javax.swing.JTextField txtdescripcionNventa;
    private javax.swing.JTextField txtdescripcionproducto;
    private javax.swing.JTextField txtdireccionclienteventa;
    private javax.swing.JTextField txtdireccionconfig;
    private javax.swing.JTextField txtdireccionproveedor;
    private javax.swing.JTextField txtidcliente;
    private javax.swing.JTextField txtidconfig;
    private javax.swing.JTextField txtidentidicacionventa;
    private javax.swing.JTextField txtidentificacioncliente;
    private javax.swing.JTextField txtidentificacionusu;
    private javax.swing.JTextField txtidprod;
    private javax.swing.JTextField txtidproducto;
    private javax.swing.JTextField txtidprovedor;
    private javax.swing.JTextField txtidusuario;
    private javax.swing.JTextField txtnom_empreaCONF;
    private javax.swing.JTextField txtnombrecliente;
    private javax.swing.JTextField txtnombreclienteventa;
    private javax.swing.JTextField txtnombreproveedor;
    private javax.swing.JTextField txtnombreusuario;
    private javax.swing.JTextField txtpassusuario;
    private javax.swing.JTextField txtprecionuevaventa;
    private javax.swing.JTextField txtprecioproducto;
    private javax.swing.JComboBox<String> txtproveproducto;
    private javax.swing.JTextField txtrazonclienteventa;
    private javax.swing.JTextField txtrazonsociconfigu;
    private javax.swing.JTextField txtrollusuario;
    private javax.swing.JTextField txtrutconfig;
    private javax.swing.JTextField txtrutprovedor;
    private javax.swing.JTextField txtstockdisponible;
    private javax.swing.JTextField txttelefonoclienteventa;
    private javax.swing.JTextField txttelefonoconfig;
    private javax.swing.JTextField txttelefonoproveedor;
    private javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables
private void limpiarCliente() {
        txtidcliente.setText("");
        txtidentificacioncliente.setText("");
        txtnombrecliente.setText("");
        txt_telefonocliente.setText("");
        txt_direccioncliente.setText("");
        txtRazoncliente.setText("");
    }

    private void limpiarProveedor() {
        txtidprovedor.setText("");
        txtrutprovedor.setText("");
        txtnombreproveedor.setText("");
        txttelefonoproveedor.setText("");
        txtdireccionproveedor.setText("");
        txtRazonproveedor.setText("");
    }

    private void limpiarProducto() {
        txtidproducto.setText("");
        txtcodigoproducto.setText("");
        txtdescripcionproducto.setText("");
        txtproveproducto.setSelectedItem(null);
        txtcantidadproducto.setText("");
        txtprecioproducto.setText("");
    }

    private void TotalPagar() {
        totalPagar = 0.00;
        int numfila = tablaVentaNueva.getRowCount();
        for (int i = 0; i < numfila; i++) {
            double cal = Double.parseDouble(String.valueOf(tablaVentaNueva.getModel().getValueAt(i, 4)));
            totalPagar = totalPagar + cal;

        }
        TotalApagar.setText(String.format("% .2f", totalPagar));

    }

    private void LimpiarVenta() {
        txtcodigoventa.setText("");
        txtdescripcionNventa.setText("");
        txtcantidadNventa.setText("");
        txtprecionuevaventa.setText("");
        txtstockdisponible.setText("");
        TXTidventa.setText("");
    }

    private void RegistrarVenta() {

        String cliente = txtnombreclienteventa.getText();
        String vendedor = txtVendedor.getText();
        double total = totalPagar;
        v.setCliente(cliente);
        v.setVendedor(vendedor);
        v.setTotal(total);
        vDAO.Registrarvent(v);
    }

    private void RegistrarDetalle() {
        int id = vDAO.IdVenta();
        for (int i = 0; i < tablaVentaNueva.getRowCount(); i++) {
            String cod = tablaVentaNueva.getValueAt(i, 0).toString();
          
            double canti = Double.parseDouble(tablaVentaNueva.getValueAt(i, 2).toString());
            double prec = Double.parseDouble(tablaVentaNueva.getValueAt(i, 3).toString());

            Dv.setCod_producto(cod);
            
            Dv.setCantidad(canti);
            Dv.setPrecio(prec);
            Dv.setId_venta(id);
            vDAO.RegistrarDetalle(Dv);
        }

    }

    private void ActualizarStock() {

        for (int i = 0; i < tablaVentaNueva.getRowCount(); i++) {
            String cod = tablaVentaNueva.getValueAt(i, 0).toString();
            double cant = Double.parseDouble(tablaVentaNueva.getValueAt(i, 2).toString());
            pro = prodDAO.BuscarPro(cod);
            int stockActual = (int) (pro.getStock() - cant);
            vDAO.ActualizarStock(stockActual, cod);

        }

    }

    private void Limpiartablenventa() {
        tpm = (DefaultTableModel) tablaVentaNueva.getModel();
        int fila = tablaVentaNueva.getRowCount();
        for (int i = 0; i < fila; i++) {
            tpm.removeRow(0);
        }

    }

    private void LimpiarClienteVenta() {

        txtidentificacioncliente.setText("");
        txtnombreclienteventa.setText("");
        txttelefonoclienteventa.setText("");
        txtdireccionclienteventa.setText("");
        txtrazonclienteventa.setText("");

    }

    private void limpiarlogin() {
        txtidusuario.setText("");
        txtidentificacionusu.setText("");
        txtnombreusuario.setText("");
        txtusuario.setText("");
        txtpassusuario.setText("");

        txtrollusuario.setText("");

    }

    private void buscarcliente() {
        if (!"".equals(txtidentificacioncliente.getText())) {
            String ident = txtidentificacioncliente.getText();
            cl = client.BuscarCliente(ident);
            if (cl.getNombre() != null) {
                txtnombrecliente.setText(" " + cl.getNombre());
                txt_telefonocliente.setText(" " + cl.getTelefono());
                txt_direccioncliente.setText(" " + cl.getDireccion());
                txtRazoncliente.setText("" + cl.getRazonsocial());
                txtidentificacioncliente.requestFocus();

            } else {

                LimpiarVenta();
                txtidentificacioncliente.requestFocus();
            }
        }
    }

    void buscarProducto() {

        if (!"".equals(txtcodigoproducto.getText())) {
            String cod = txtcodigoproducto.getText();
            pro = prodDAO.BuscarPro(cod);
            if (pro.getDescripcion() != null) {
                txtdescripcionproducto.setText(" " + pro.getDescripcion());
                txtprecioproducto.setText(" " + pro.getPrecio());
                txtcantidadproducto.setText(" " + pro.getStock());
                txtproveproducto.setSelectedItem("" + pro.getProveedor());
                txtcantidadNventa.requestFocus();

            } else {

                LimpiarVenta();
                txtcodigoventa.requestFocus();

            }
        }
    }

    private void buscarproveedor() {

        if (!"".equals(txtrutprovedor.getText())) {
            int rut = Integer.parseInt((String.valueOf(txtrutprovedor.getText())));
            Proveedor pr = prDao.BuscarProveedor(rut);
            if (pr.getNombre() != null) {
                txtnombreproveedor.setText(" " + pr.getNombre());
                txttelefonoproveedor.setText(" " + pr.getTelefono());
                txtdireccionproveedor.setText(" " + pr.getDireccion());
                txtRazonproveedor.setText("" + pr.getRazon());
                txtrutprovedor.requestFocus();

            } else {

                limpiarProveedor();
                txtrutprovedor.requestFocus();
            }
        }

    }

    public void buscarUsuario() {

        if (!"".equals(txtidentificacionusu.getText())) {
            String identificacion = txtidentificacionusu.getText();
            login logi = logDAO.BuscarUsuario(identificacion);

            if (logi.getNombre() != null) {
                txtnombreusuario.setText(" " + logi.getNombre());
                txtusuario.setText(" " + logi.getUsuario());
                txtpassusuario.setText(" " + logi.getPass());
                txtrollusuario.setText(" " + logi.getRoll());
                txtidentificacionusu.requestFocus();

            } else {

                txtidentificacionusu.requestFocus();

            }

        }

    }

    private void BuscarConf() {

        if (!"".equals(txtrutconfig.getText())) {
            int rut = (Integer.parseInt(String.valueOf(txtrutconfig.getText())));
            config conf = prodDAO.BuscarDatos();

            if (conf.getNombre_empresa() != null) {
                txtnom_empreaCONF.setText(" " + conf.getNombre_empresa());
                txttelefonoconfig.setText(" " + conf.getTelefono());
                txtdireccionconfig.setText(" " + conf.getDireccion());
                txtrazonsociconfigu.setText(" " + conf.getRazon());
                txtrutconfig.requestFocus();

            } else {

                txtidentificacionusu.requestFocus();

            }

        }

    }

    private void limpiarconfigu() {
        txtidconfig.setText("");
        txtnom_empreaCONF.setText("");
        txtrutconfig.setText("");
        txttelefonoconfig.setText("");
        txtdireccionconfig.setText("");

        txtrazonsociconfigu.setText("");

    }
    
   
    private  void Rpdf() throws FileNotFoundException, DocumentException, IOException{
        
    int id= vDAO.IdVenta();
    Document documento=new Document();
    File file = new File ("src/pdf"+id+"venta.pdf");
    FileOutputStream ficheropdf= new FileOutputStream( file);
    PdfWriter.getInstance(documento, ficheropdf);
    
    documento.open();
    
    Paragraph titulo= new Paragraph("FACTURA DE VENTA FERRETERIA LA 4 \n\n",
    
            FontFactory.getFont("arial",
                    
                    22,
                    Font.BOLD,
                    BaseColor.BLACK
                    )
    
    );
    documento.add(titulo);
  
  Paragraph fecha= new Paragraph();
  fecha.add(Chunk.NEWLINE);
  Date date=new Date();
  fecha.add("FACTURA No:"+id+"\n\nFECHA:"+ new SimpleDateFormat("dd-MM-yyyy").format(date)+"\n\n");
  fecha.setAlignment(Element.ALIGN_RIGHT);
  
  documento.add(fecha);
    Paragraph encabezado= new Paragraph();
    
    String ruc=txtrutconfig.getText();
    String nombre= txtnom_empreaCONF.getText();
    String direccion=txtdireccionconfig.getText();
    String telefono=txttelefonoconfig.getText();
 
    encabezado.add("RUT:" +  ruc+ "\n\n NOMBRE DE LA EMPRESA:"  +nombre+ "\n\n DIRECCION:" +    direccion+"\n\n TELEFONO:" +   telefono );
    documento.add(encabezado);
    Paragraph cli= new Paragraph();
    cli.add(Chunk.NEWLINE);
    cli.add("DATO DEL CLIENTE:"+"\n\n");
    
   documento.add(cli);
   
   PdfPTable tablacli= new PdfPTable(4);
            
    tablacli.setWidthPercentage(100);
    tablacli.getDefaultCell().setBorder(0);
    float[]Columnacli= new float[]{20f, 30f, 40f, 30f};
    tablacli.setWidths(Columnacli);
    tablacli.setHorizontalAlignment(Element.ALIGN_LEFT);
        PdfPCell cl1= new PdfPCell(new Phrase("identificacion:"));
        PdfPCell cl2= new PdfPCell(new Phrase("Nombre:"));
        PdfPCell cl3= new PdfPCell(new Phrase("telefono:"));
        PdfPCell cl4= new PdfPCell(new Phrase("direccion:"));
        cl1.setBorder(0);
        cl2.setBorder(0);
        cl3.setBorder(0);
        cl4.setBorder(0);
        tablacli.addCell(cl1);
        tablacli.addCell(cl2);
        tablacli.addCell(cl3);
        tablacli.addCell(cl4);
        tablacli.addCell(txtidentidicacionventa.getText());
        tablacli.addCell(txtnombreclienteventa.getText());
        tablacli.addCell(txttelefonoclienteventa.getText());
        tablacli.addCell(txtdireccionclienteventa.getText());
        
        
        documento.add(tablacli);
        
        
        //se agrega la tabla de los productos
        
        PdfPTable tablapro= new PdfPTable(4);
            
    tablapro.setWidthPercentage(100);
    tablapro.getDefaultCell().setBorder(0);
    float[]Columnapro= new float[]{20f, 50f, 40f, 30f};
    tablapro.setWidths(Columnapro);
    tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
        PdfPCell pro1= new PdfPCell(new Phrase("cantidad.",  FontFactory.getFont("arial",
                    
                    14,
                    Font.BOLD,
                    BaseColor.BLUE)));
        PdfPCell pro2= new PdfPCell(new Phrase("Descripcion:",  FontFactory.getFont("arial",
                    
                    14,
                    Font.BOLD,
                    BaseColor.BLUE)));
        PdfPCell pro3= new PdfPCell(new Phrase("precio U:",  FontFactory.getFont("arial",
                    
                    14,
                    Font.BOLD,
                    BaseColor.BLUE)));
        PdfPCell pro4= new PdfPCell(new Phrase(" precio Total:",  FontFactory.getFont("arial",
                    
                    14,
                    Font.BOLD,
                    BaseColor.BLUE)));
        pro1.setBorder(0);
        pro2.setBorder(0);
        pro3.setBorder(0);
        pro4.setBorder(0);
        tablapro.addCell(pro1);
        tablapro.addCell(pro2);
        tablapro.addCell(pro3);
        tablapro.addCell(pro4);
        
        
        for (int i = 0; i < tablaVentaNueva.getRowCount(); i++) {
            String producto= tablaVentaNueva.getValueAt(i, 1).toString();
            String cantidad= tablaVentaNueva.getValueAt(i, 2).toString();
              String precio= tablaVentaNueva.getValueAt(i, 3).toString();
               String total= tablaVentaNueva.getValueAt(i, 4).toString();
            
        tablapro.addCell(cantidad);
        tablapro.addCell(producto);
        tablapro.addCell(precio);
        tablapro.addCell(total);
        
        
        }
        
    documento.add(tablapro);
    
    Paragraph info= new Paragraph();
    String total=TotalApagar.getText();
    info.add(Chunk.NEWLINE);
    info.add("TOTAL A PAGAR:"+ total);
    info.setAlignment(Element.ALIGN_RIGHT);
      
    documento.add(info);
    
    Paragraph firma= new Paragraph();
    firma.add(Chunk.NEWLINE);
    firma.add("cancelacion y firma");
    firma.add("---------------------------");
    firma.setAlignment(Element.ALIGN_CENTER);
    documento.add(firma);
    
    
    Paragraph mensaje= new Paragraph();
    mensaje.add(Chunk.NEWLINE);
    
    mensaje.add("GRACIAS POR SU COMPRA");
    mensaje.setAlignment(Element.ALIGN_CENTER);
    documento.add(mensaje);
    
    
    documento.close();
        Desktop.getDesktop().open(file );
    
    
    
        }

    }

    

