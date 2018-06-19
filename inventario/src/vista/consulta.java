/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import dao.FiltroDao;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import static jdk.nashorn.internal.runtime.GlobalFunctions.parseInt;
import modelo.Filtro;

/**
 *
 * @author Diego Polanco
 */
public class consulta extends JFrame {
    
    public JLabel lbCodigo, lbMarca,lbStock,lbExistencia;
    
    public JTextField codigo, descripcion, stock;
    public JComboBox marca;
    
    ButtonGroup existencia = new ButtonGroup();
    public JRadioButton no;
    public JRadioButton si;
    public JTable resultados;
    
    public JPanel table;
    
    public JButton buscar,eliminar,insertar,limpiar,actualizar;
    
    private static final int ANCHOC =130,ALTOC=30;
    
    DefaultTableModel tm;
    
public consulta(){
    super ("Inventarios");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(null);
   agregarlabels();
   formulario();
   llenarTabla();
   Container container=getContentPane();
   container.add(lbCodigo);
   container.add(lbMarca);
   container.add(lbStock);
   container.add(si);
   container.add(no);
   container.add(buscar);
   container.add(insertar);
   container.add(actualizar);
   container.add(eliminar);
   container.add(table);
   setSize(600, 600);
   eventos();
    
}
public final void agregarlabels(){
  lbCodigo = new JLabel("CODIGO");
  lbMarca = new JLabel("MARCA");
  lbStock = new JLabel("Stock");
  lbExistencia = new JLabel("Stock en tienda");
  lbCodigo.setBounds(10,10,ANCHOC,ALTOC);
  lbMarca.setBounds(10,60,ANCHOC,ALTOC);
  lbStock.setBounds(10,140,ANCHOC,ALTOC);
  lbExistencia.setBounds(10,140, ANCHOC,ALTOC);
}
public final void formulario(){
    //ELEMENTOS
    codigo = new JTextField();
    marca = new JComboBox();
    stock = new JTextField();
    si = new JRadioButton("SI",true);
    no = new JRadioButton("NO");
    resultados= new JTable();
    buscar = new JButton("Buscar");
    insertar = new JButton("INSERTAR");
    eliminar = new JButton("eliminar");
    actualizar = new JButton("Actualizar");
    limpiar= new JButton("LIMPIAR");
    
    table = new JPanel();
    //AGREGAR ELEMENTOS AL COMBOBOX MARCA
    marca.addItem("FRAM");
    marca.addItem("WIX");
    marca.addItem("Luber Finer");
    marca.addItem("OSK");
    //AGREGANDO LOS RADIO A UN GRUPO
    existencia = new ButtonGroup();
    existencia.add(si);
    existencia.add(no);
    
codigo.setBounds(140,10,ANCHOC,ALTOC);
marca.setBounds(140,60,ANCHOC,ALTOC);
stock.setBounds(140,100,ANCHOC,ALTOC);
si.setBounds(140,140,50, ALTOC);
no.setBounds(210,140,50,ALTOC);

buscar.setBounds(300,10,ANCHOC,ALTOC);
insertar.setBounds(10,210,ANCHOC,ALTOC);
actualizar.setBounds(150,210,ANCHOC,ALTOC);
eliminar.setBounds(300,210,ANCHOC,ALTOC);
limpiar.setBounds(450,210,ANCHOC,ALTOC);
resultados = new JTable();
table.setBounds(10,250,500,200);
table.setBounds(10,250,500,200);
table.add(new JScrollPane(resultados));

  
   
}
public void llenarTabla(){
//ACA colocaremos el tipo de dato que tendra nuestras columnas
//si un dato boolean aparecera un checkbox en el JTable
tm =new DefaultTableModel(){
    public Class <?> getColumClass(int column){
        switch (column){
        case 0:
        return String.class ;
               case 1:
        return String.class ;
        case 2:
        return String.class ;
        default:
        return Boolean.class ;
    }
}    
};
//agregamos las columnas que se mostraran con su respectivo nombre
tm.addColumn("CODIGO");
tm.addColumn("Marca");
tm.addColumn("STOCK");
tm.addColumn("Stock en sucursal");

//Realizaremos la consulta a nuestra base de datos por medio del metodo READALL
FiltroDao fd = new FiltroDao();
ArrayList<Filtro> filtros = fd.readAll();
//agregar resultado a jtable
//se agragan de tipo object
for (Filtro fi: filtros){
            tm.addRow(new Object[]{fi.getCodigo(), fi.getMarca(), fi.getStock(), fi.isExistencia()});
        }
        //LE AGREGAMOS EL MODELO DE NUESTRA TABLA
        resultados.setModel(tm);
        
    }

    private void eventos() {
        //INSERTAR
        insertar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                FiltroDao fd= new FiltroDao();
                Filtro f= new Filtro(codigo.getText(), marca.getSelectedItem().toString(),
                    Integer.parseInt(stock.getText()),true);
                
                if(no.isSelected()){
                    f.setExistencia(false);
                }
                if (fd.create(f)){
                    JOptionPane.showMessageDialog(null, "Filtro registrado con exito");
                    limpiarCampos();
                    llenarTabla();
                }else{
                    JOptionPane.showMessageDialog(null,"Ocurrio un problema al momento de crear el fltro");
                }
            }

            private void limpiarCampos() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        actualizar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                FiltroDao fd= new FiltroDao();
                Filtro f= new Filtro (codigo.getText(), marca.getSelectedItem().toString(),
                            Integer.parseInt(stock.getText()), true);
                
                if(no.isSelected()){
                    f.setExistencia(false);
                }
                if (fd.update(f)){
                    JOptionPane.showMessageDialog(null, "Filtro modificado con exito");
                    limpiarCampos();
                    llenarTabla();
                }else{
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema al momento de modificar el filtro");
                }
            }

            private void limpiarCampos() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        eliminar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                FiltroDao fd= new FiltroDao();
                if(fd.delete(codigo.getText())){
                    JOptionPane.showMessageDialog(null, "Filtro eliminado con exito");
                    limpiarCampos();
                    llenarTabla();
                }else{
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema al momento de eliminar el filtro");
                }
            }            
        });
        
        buscar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                FiltroDao fd =new FiltroDao();
                Filtro f=fd.read(codigo.getText());
                if(f== null){
                    JOptionPane.showMessageDialog(null,"El filtro buscado no de ha encontrado");
                }else{
                    codigo.setText(f.getCodigo());
                    marca.setSelectedItem(f.getMarca());
                    stock.setText(Integer.toString(f.getStock()));
                    
                    if(f.isExistencia()){
                        si.setSelected(true);
                    }else{
                        no.setSelected(true);
                    }
                }
            }
            
        });
        
        limpiar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
            
        });
    }
    public void limpiarCampos(){
        codigo.setText("");
        marca.setSelectedItem("FRAM");
        stock.setText("");
    }
    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
                new consulta().setVisible(true);
            }
            
        });
    }
    
}


