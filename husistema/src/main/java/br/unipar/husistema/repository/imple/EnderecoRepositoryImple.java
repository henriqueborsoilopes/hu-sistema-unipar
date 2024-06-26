package br.unipar.husistema.repository.imple;

import br.unipar.husistema.entity.Endereco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import br.unipar.husistema.repository.IEnderecoRepository;
import br.unipar.husistema.service.connection.ConexaoBD;
import br.unipar.husistema.service.exception.BancoDadosExcecao;

public class EnderecoRepositoryImple implements IEnderecoRepository {
    
    private static final String TABELA = "endereco";
    private static final String[] COLUNAS = {"id", "logradouro", "numero", "complemento", "bairro", "cidade", "uf", "cep"};
    
    @Override
    public Endereco inserir(Endereco endereco) {
        String query = ""
            + "INSERT INTO " + TABELA + " (" + COLUNAS[1] + ", " + COLUNAS[2] + ", " + COLUNAS[3] + ", " + COLUNAS[4] + ", " + COLUNAS[5] + ", " + COLUNAS[6] + ", " + COLUNAS[7] + ") "
            + "VALUES (?, ?, ?, ?, ?, ?, ?);";
        
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, endereco.getLogradouro());
            ps.setString(2, endereco.getNumero());
            ps.setString(3, endereco.getComplemento());
            ps.setString(4, endereco.getBairro());
            ps.setString(5, endereco.getCidade());
            ps.setString(6, endereco.getUf());
            ps.setString(7, endereco.getCep());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()){
                if (rs.next()) {
                    endereco.setId(rs.getLong("id"));
                }
                return endereco;
            } catch (SQLException e) {
                throw new BancoDadosExcecao("Desculpe, ocorreu um erro ao processar sua solicitação. Por favor, tente novamente mais tarde.");
            }
        } catch (SQLException e) {
            throw new BancoDadosExcecao("Desculpe, ocorreu um erro ao processar sua solicitação. Por favor, tente novamente mais tarde.");
        }
    }
    
    @Override
    public void atualizar(Endereco endereco) {
        String query = ""
            + "UPDATE " + TABELA + " "
            + "SET " + COLUNAS[1] + " = ?, " + COLUNAS[2] + " = ?, " + COLUNAS[3] + " = ?, " + COLUNAS[4] + " = ?, " + COLUNAS[5] + " = ?, " + COLUNAS[6] + " = ?, " + COLUNAS[7] + " = ? "
            + "WHERE " + COLUNAS[0] + " = ?;";
        
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(query)) {
            ps.setString(1, endereco.getLogradouro());
            ps.setString(2, endereco.getNumero());
            ps.setString(3, endereco.getComplemento());
            ps.setString(4, endereco.getBairro());
            ps.setString(5, endereco.getCidade());
            ps.setString(6, endereco.getUf());
            ps.setString(7, endereco.getCep());
            ps.setLong(8, endereco.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new BancoDadosExcecao("Desculpe, ocorreu um erro ao processar sua solicitação. Por favor, tente novamente mais tarde.");
        }
    }
}
