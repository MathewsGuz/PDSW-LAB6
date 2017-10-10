/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.simpleview;

import edu.eci.pdsw.persistence.impl.mappers.EpsMapper;
import edu.eci.pdsw.persistence.impl.mappers.PacienteMapper;
import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Eps;
import edu.eci.pdsw.samples.entities.Paciente;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author hcadavid
 */
public class MyBATISExample {

/**
     * Método que construye una fábrica de sesiones de MyBatis a partir del
     * archivo de configuración ubicado en src/main/resources
     *
     * @return instancia de SQLSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getLocalizedMessage(),e);
            }
        }
        return sqlSessionFactory;
    }

    /**
     * Programa principal de ejempo de uso de MyBATIS
     * @param args
     * @throws SQLException 
     */
    public static void main(String args[]) throws SQLException {
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        SqlSession sqlss = sessionfact.openSession();
        PacienteMapper pmapper=sqlss.getMapper(PacienteMapper.class);
        EpsMapper emapper = sqlss.getMapper(EpsMapper.class);

        List<Paciente> pacientes=pmapper.loadPacientes();
        for(Paciente i:pacientes){
            System.out.println(i.getNombre());
        }
        Paciente pruebaPaciente=pmapper.loadPacienteById(1026585441,"CC");
        System.out.println(pruebaPaciente.getNombre() + " " + pruebaPaciente.getEps().getNombre() + " " + pruebaPaciente.getConsultas().size());
        
        Paciente diego = new Paciente(1019108883,"CC","Diego Borrero",java.sql.Date.valueOf("1995-06-14"),pacientes.get(0).getEps());
//        registrarNuevoPaciente(pmapper,diego);
        
//        actualizarPaciente(pmapper,pmapper.loadPacienteById(2109950,"CC"));
        
        List<Eps> epses= emapper.loadAllEPS();
        for(Eps i: epses){
            System.out.println(i.getNombre()+" +"+i.getNit());
        }
        
//        pruebaPaciente=pmapper.loadPacienteById(1019108883,"CC");
//        System.out.println(pruebaPaciente.getNombre() + " " + pruebaPaciente.getEps().getNombre() + " " + pruebaPaciente.getConsultas().size());
        
//        pmapper.insertConsulta(new Consulta(java.sql.Date.valueOf("2018-06-14"),"amor",100000), 1019108883, "CC", 100000);
//        pruebaPaciente=pmapper.loadPacienteById(1019108883,"CC");
//        System.out.println(pruebaPaciente.getNombre() + " " + pruebaPaciente.getEps().getNombre() + " " + pruebaPaciente.getConsultas().size());

//imprimir contenido de la lista
    }

    /**
     * Registra un nuevo paciente y sus respectivas consultas (si existiesen).
     * @param pmap mapper a traves del cual se hará la operacion
     * @param p paciente a ser registrado
     */
    public static void registrarNuevoPaciente(PacienteMapper pmap, Paciente p){
     
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        SqlSession sqlss = sessionfact.openSession();
        PacienteMapper pmapper=sqlss.getMapper(PacienteMapper.class);
        
        
        pmapper.insertarPaciente(p);
        
        pmapper.insertConsulta(new Consulta(java.sql.Date.valueOf("2018-06-14"),"gripe leve",100000), 1019108883, "CC", 100000);

        sqlss.commit();	

    }
    
    /**
    * @obj Actualizar los datos básicos del paciente, con sus * respectivas consultas.
    * @pre El paciente p ya existe
    * @param pmap mapper a traves del cual se hará la operacion
    * @param p paciente a ser registrado
    */
    public static void actualizarPaciente(PacienteMapper pmap, Paciente p){
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        SqlSession sqlss = sessionfact.openSession();
        PacienteMapper pmapper=sqlss.getMapper(PacienteMapper.class);
        pmapper.updatePaciente(p.getId(), "florentina", pmapper.loadPacientes().get(0).getEps(),java.sql.Date.valueOf("2000-06-14"));
        
        for(Consulta i : p.getConsultas()){
            if(i.getId()==0 && i.getFechayHora()!=null){
                pmapper.insertConsulta(i, 2109950, "CC",(int) i.getCosto());
            }
        }
        
        sqlss.commit();	
    }

    
}
