package br.unipar.husistema.repository.imple;

import br.unipar.husistema.entity.Pessoa;
import br.unipar.husistema.factory.ConnectionFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import br.unipar.husistema.repository.IPessoaRepository;

public class PessoaRepositoryImple implements IPessoaRepository {
    
    private static final String TABELA = "pessoa";
    private static final String[] COLUNAS = {"id", "nome", "email", "telefone", "ativo", "id_endereco"};
    
    @Override
    public Pessoa inserir(Pessoa pessoa) throws SQLException {
        String query = ""
            + "INSERT INTO " + TABELA + " (" + COLUNAS[1] + ", " + COLUNAS[2] + ", " + COLUNAS[3] + ", " + COLUNAS[4] + ", " + COLUNAS[5] + ") "
            + "VALUES (?, ?, ?, ?, ?);";
        
        try (PreparedStatement ps = ConnectionFactory.getConexao().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pessoa.getNome());
            ps.setString(2, pessoa.getEmail());
            ps.setString(3, pessoa.getTelefone());
            ps.setBoolean(4, pessoa.isAtivo());
            ps.setLong(5, pessoa.getIdEndereco());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    pessoa.setId(rs.getLong(COLUNAS[0]));
                }
                return pessoa;
            }
        }
    }
    
    @Override
    public void atualizar(Pessoa pessoa) throws SQLException {
        String query = ""
            + "UPDATE " + TABELA + " "
            + "SET " + COLUNAS[1] + " = ?, " + COLUNAS[3] + " = ? "
            + "WHERE " + COLUNAS[0] + " = ?;";
        
        try (PreparedStatement ps = ConnectionFactory.getConexao().prepareStatement(query)) {
            ps.setString(1, pessoa.getNome());
            ps.setString(2, pessoa.getTelefone());
            ps.setLong(3, pessoa.getId());
            ps.execute();
        }
    }
    
    @Override
    public void inativar(Long id) throws SQLException {
        String query = ""
            + "UPDATE " + TABELA + " "
            + "SET " + COLUNAS[4] + " = ? "
            + "WHERE " + COLUNAS[0] + " = ?;";
        
        try (PreparedStatement ps = ConnectionFactory.getConexao().prepareStatement(query)) {
            ps.setBoolean(1, false);
            ps.setLong(2, id);
            ps.execute();
        }
    }
}
