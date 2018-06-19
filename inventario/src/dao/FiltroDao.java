/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexion.Conexion;
import interfaces.metodos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Filtro;

/**
 *
 * @author LN710Q
 */
public class FiltroDao implements metodos<Filtro>{
private static final String SQL_INSERT="INSERT INTO filtros_aceite(codFiltro,Marca,Stock,existencia)Values(?,?,?,?)";
private static final String SQL_UPDATE="Update filtros_aceite SET marca =?,Stock=?,existencia=?WHERE codFiltro=?";
private static final String SQL_DELETE="DELETE FROM filtros_aceite WHERE codFiltro=?";
private static final String SQL_READ="SELECT* FROM filtros_aceite WHERE codFiltro=?";
private static final String SQL_REDALL="SELECT *FROM filtros_aceite";

private static final Conexion con=Conexion.conectar();
    @Override
    public boolean create(Filtro g) {
        PreparedStatement ps;
        try{
            ps=con.getCnx().prepareStatement(SQL_INSERT);
            ps.setString(1,g.getCodigo());
            ps.setString(3, g.getMarca());
            ps.setInt(3, g.getStock());
            ps.setBoolean(4, true);
            if(ps.executeUpdate()>0){
                
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        Logger.getLogger(FiltroDao.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
            con.cerrarconexion();
        }
        return false;
    }

    @Override
    public boolean delete(Object key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Filtro c) {
        PreparedStatement ps;
        try{
            System.out.println(c.getCodigo());
            ps=con.getCnx().prepareStatement(SQL_UPDATE);
            ps.setString(1, c.getMarca());
            ps.setInt(2, c.getStock());
            ps.setBoolean(3,c.isExistencia());
            ps.setString(4, c.getCodigo());
            
            if (ps.executeUpdate() >0)
            {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        Logger.getLogger(FiltroDao.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
            con.cerrarconexion();
        }
        return false;
    }

    @Override
    public Filtro read(Object key) {
        Filtro f=null;
            PreparedStatement ps;
            ResultSet rs;
            try{
                ps=con.getCnx().prepareStatement(SQL_READ);
                ps.setString(1, key.toString());
                
                rs=ps.executeQuery();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
        Logger.getLogger(FiltroDao.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
                con.cerrarconexion();
            }
            
        return f;
    }

    @Override
    public ArrayList<Filtro> readAll() {
        ArrayList<Filtro> all = new ArrayList();
        Statement s;
        ResultSet rs;
        try{
            s=con.getCnx().prepareStatement(SQL_REDALL);
            rs=s.executeQuery(SQL_READ);
            
        } catch (SQLException ex) {
        Logger.getLogger(FiltroDao.class.getName()).log(Level.SEVERE, null, ex);
    }
        return all;
    }
    
    
}
