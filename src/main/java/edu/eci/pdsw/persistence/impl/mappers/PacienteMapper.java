package edu.eci.pdsw.persistence.impl.mappers;



import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Eps;
import edu.eci.pdsw.samples.entities.Paciente;
import java.sql.Date;

import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author 2106913
 */
public interface PacienteMapper {
        
    public Paciente loadPacienteById(@Param("idp") int id,@Param("tipoidp") String tipoid);
    
    public List<Paciente> loadPacientes();
    
    public void insertarPaciente(@Param("p") Paciente p);
    
    public void insertConsulta( @Param("cons") Consulta con,@Param("idp") int idPaciente,@Param("tipoidp") String tipoid,@Param("costoc") int costoconsulta);
    
    public void updatePaciente( @Param("idp") int id, @Param("nomp") String nombre,@Param("epsp") Eps eps, @Param("fechanacimientop") Date fechaNacimiento);
    

}
