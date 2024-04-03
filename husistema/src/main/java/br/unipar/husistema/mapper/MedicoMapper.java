package br.unipar.husistema.mapper;

import br.unipar.husistema.dto.InserirMedicoDTO;
import br.unipar.husistema.dto.ListMedicoDTO;
import br.unipar.husistema.entity.Medico;
import br.unipar.husistema.entity.enums.EspecialidadeEnum;
import java.util.List;
import java.util.stream.Collectors;

public class MedicoMapper {
    
    public static Medico getEntity(InserirMedicoDTO dto) {
        return new Medico(
                null, 
                dto.getNome(), 
                dto.getEmail(), 
                dto.getTelefone(), 
                true, 
                null, 
                dto.getCrm(), 
                EspecialidadeEnum.paraEnum(dto.getTipoEspecialidade()));
    }
    
    public static ListMedicoDTO getDTO(Medico entity) {
        return new ListMedicoDTO(
                entity.getId(),
                entity.getNome(), 
                entity.getEmail(), 
                entity.getCrm(), 
                entity.getTipoEspecialidade().getDescricao());
    }

    public static List<ListMedicoDTO> getLitDTO(List<Medico> entities) {
        return entities.stream().map(x -> getDTO(x)).collect(Collectors.toList());
    }
}
