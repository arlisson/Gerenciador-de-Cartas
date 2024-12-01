/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import Model.Carta;
import Principal.Projeto_YGO;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author T-GAMER
 */
public class CartaDAO {
	private Connection connection;

	private final String SELECT = "SELECT * FROM cartas ORDER BY nome_carta ASC";
	private final String SELECT_BY_ID = "SELECT * FROM cartas WHERE id= ?";
	private final String INSERT = "INSERT INTO cartas(nome_carta, codigo, colecao, ano_compra, valor_pago, raridade, qualidade,imagem ) VALUES (?,?,?,?,?,?,?,?)";
	private final String SELECT_BY_NAME = "SELECT * FROM cartas ORDER BY nome_carta ASC";
	private final String SELECT_BY_NAME_DESC = "SELECT * FROM cartas ORDER BY nome_carta DESC";
	private final String SELECT_BY_CODIGO = "SELECT * FROM cartas ORDER BY codigo ASC";
	private final String SELECT_BY_CODIGO_DESC = "SELECT * FROM cartas ORDER BY codigo DESC";
	private final String SELECT_BY_COLECAO = "SELECT * FROM cartas ORDER BY colecao ASC";
	private final String SELECT_BY_COLECAO_DESC = "SELECT * FROM cartas ORDER BY colecao DESC";
	private final String SELECT_BY_ANO_COMPRA = "SELECT * FROM cartas ORDER BY ano_compra ASC";
	private final String SELECT_BY_ANO_COMPRA_DESC = "SELECT * FROM cartas ORDER BY ano_compra DESC";
	private final String SELECT_BY_ANO_VENDA = "SELECT * FROM cartas ORDER BY ano_venda ASC";
	private final String SELECT_BY_ANO_VENDA_DESC = "SELECT * FROM cartas ORDER BY ano_venda DESC";
	private final String SELECT_BY_VALOR_COMPRA = "SELECT * FROM cartas ORDER BY valor_pago ASC";
	private final String SELECT_BY_VALOR_COMPRA_DESC = "SELECT * FROM cartas ORDER BY valor_pago DESC";
	private final String SELECT_BY_VALOR_VENDA = "SELECT * FROM cartas ORDER BY valor_venda ASC";
	private final String SELECT_BY_VALOR_VENDA_DESC = "SELECT * FROM cartas ORDER BY valor_venda DESC";
	private final String SELECT_NAME = "SELECT * FROM cartas WHERE nome_carta LIKE ? ORDER BY nome_carta ASC";
	private final String SELECT_NAME_DESC = "SELECT * FROM cartas WHERE nome_carta LIKE ? ORDER BY nome_carta DESC";
	private final String SELECT_ANO_COMPRA = "SELECT * FROM cartas WHERE ano_compra LIKE ? ORDER BY ano_compra ASC";
	private final String SELECT_ANO_VENDA = "SELECT * FROM cartas WHERE ano_venda LIKE ? ORDER BY ano_ venda ASC";
	private final String SELECT_CODIGO = "SELECT * FROM cartas WHERE codigo LIKE ? ORDER BY codigo ASC";
	private final String SELECT_COLECAO = "SELECT * FROM cartas WHERE colecao LIKE ? ORDER BY colecao ASC";
	private final String SELECT_ANO_COMPRA_DESC = "SELECT * FROM cartas WHERE ano_compra LIKE ? ORDER BY ano_compra DESC";
	private final String SELECT_ANO_VENDA_DESC = "SELECT * FROM cartas WHERE ano_venda LIKE ? ORDER BY ano_ venda DESC";
	private final String SELECT_CODIGO_DESC = "SELECT * FROM cartas WHERE codigo LIKE ? ORDER BY codigo DESC";
	private final String SELECT_COLECAO_DESC = "SELECT * FROM cartas WHERE colecao LIKE ? ORDER BY colecao DESC";
	private final String SELECT_VALOR_COMPRA = "SELECT * FROM cartas WHERE valor_pago LIKE ? ORDER BY valor_pago ASC";
	private final String SELECT_VALOR_COMPRA_DESC = "SELECT * FROM cartas WHERE valor_pago LIKE ? ORDER BY valor_pago DESC";
	private final String SELECT_VALOR_VENDA = "SELECT * FROM cartas WHERE valor_venda LIKE ? ORDER BY valor_venda ASC";
	private final String SELECT_VALOR_VENDA_DESC = "SELECT * FROM cartas WHERE valor_venda LIKE ? ORDER BY valor_venda DESC";
	private final String UPDATE_CARTA = "UPDATE cartas SET nome_carta =?, codigo=?,colecao=?,ano_compra=?,valor_pago=?,ano_venda=?,valor_venda=?,raridade =?,quantidade=?,qualidade=?, imagem =? WHERE id = ?";
	private final String DELETE = "DELETE FROM cartas WHERE id = ?";
	private final String UPDATE_CARTA_SEM_FOTO = "UPDATE cartas SET nome_carta =?, codigo=?,colecao=?,ano_compra=?,valor_pago=?,ano_venda=?,valor_venda=?,raridade =?,quantidade=?,qualidade=? WHERE id = ?";

	private final String VENDA = "UPDATE cartas SET quantidade = quantidade-1 WHERE id =?";
	private final String INSERT_VENDA = "INSERT INTO cartas(nome_carta, codigo, colecao, ano_compra, valor_pago, raridade, qualidade,imagem,ano_venda,valor_venda ) VALUES (?,?,?,?,?,?,?,?,?,?)";

	private final String VENDIDAS = "SELECT * FROM cartas WHERE ano_venda!=\"Não vendida\" ORDER BY nome_carta ASC";
	private final String NAO_VENDIDAS = "SELECT * FROM cartas WHERE ano_venda=\"Não vendida\" ORDER BY nome_carta ASC";
	private final String VENDIDAS_DESC = "SELECT * FROM cartas WHERE ano_venda!=\"Não vendida\" ORDER BY nome_carta DESC";
	private final String NAO_VENDIDAS_DESC = "SELECT * FROM cartas WHERE ano_venda=\"Não vendida\" ORDER BY nome_carta DESC";

	private final String SELECT_NOME_BUSCA = "SELECT * FROM cartas where nome_carta like ?";
	private final String SELECT_COLECAO_BUSCA = "SELECT * FROM cartas WHERE colecao LIKE ? GROUP BY colecao";
	private final String SELECT_QUANTIDADE = "SELECT * FROM cartas ORDER by quantidade asc";
	private final String SELECT_QUANTIDADE_DESC = "SELECT * FROM cartas ORDER by quantidade DESC";
        
        private final String SELECT_ALL = 
                "SELECT * FROM cartas WHERE nome_carta LIKE ? OR colecao LIKE ? OR codigo LIKE ? OR ano_compra LIKE ? OR ano_venda LIKE ? OR raridade LIKE ? OR qualidade LIKE ? "
                + "ORDER BY CASE WHEN nome_carta LIKE ? THEN nome_carta "
                + "WHEN colecao LIKE ? THEN colecao "
                + "WHEN codigo LIKE ? THEN codigo "
                + "WHEN ano_compra LIKE ? THEN ano_compra "
                + "WHEN ano_venda LIKE ? THEN ano_venda "
                + "WHEN raridade LIKE ? THEN raridade "
                + "WHEN qualidade LIKE ? THEN qualidade END";
        
        private final String RARIDADE = "SELECT * FROM raridade";
        private final String QUALIDADE = "SELECT * FROM qualidade";

        private static CartaDAO INSTANCE =null;
        
    /**
     *
     * @return Retorna uma instância de CartaDAO
     */
    public static CartaDAO getInstance(){
            if(INSTANCE==null){
                INSTANCE = new CartaDAO();
            }
            return INSTANCE;
        }
        
	private CartaDAO() {
		try {
                        //Class.forName("com.msql.jdb.Driver");
			connection = DriverManager.getConnection("jdbc:sqlite:src/Resources/yugioh.db");
                        //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/yugioh?user=root");
                        connection.setAutoCommit(false);
		} catch (SQLException e) {
                    //JOptionPane.showMessageDialog(null,"Erro:"+Arrays.toString(e.getStackTrace()),"Erro ao conectar banco.",JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(null,"Verifique a existência do banco de dados.","Erro ao conectar com o banco.",JOptionPane.INFORMATION_MESSAGE);
		}
	}   

    /**
     *
     * @return Retorna uma conexão com o Banco de Dados
     */
    public Connection getConnection(){
            return this.connection;
        }
        
    /**
     * Fecha a conexão com o Banco
     */
    public void closeConnection(){
            try {
                this.connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(CartaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    /**
     *
     * @return Retorna uma lista de raridades cadastradas no banco
     */
    public List<String> raridade() {
		try {			
			
			PreparedStatement ps;
			ps = connection.prepareStatement(RARIDADE);			
			ResultSet result = ps.executeQuery();
                        List<String> raridade = new ArrayList<>();
			int i = 0;
			while (result.next()) {
				raridade.add(result.getString("descricao"));

				i++;

			}
                        connection.commit(); 
                        
                        ps.close();                        
                        
                       
			return raridade;

		} catch (SQLException e) {
			return null;
		}
                
        }
        
    /**
     *
     * @return Retorna uma lista de qualidades cadastradas no banco
     */
    public List<String> qualidade() {
		try {			
			
			PreparedStatement ps;
			ps = connection.prepareStatement(QUALIDADE);			
			ResultSet result = ps.executeQuery();
                        List<String> qualidade = new ArrayList<>();
			int i = 0;
			while (result.next()) {
				qualidade.add(result.getString("descricao"));

				i++;

			}
                        connection.commit(); 
                        ps.close();                        
                        
			return qualidade;

		} catch (SQLException e) {
			return null;
		}
        }
	
    /**
     *
     * @param nome
     * @return
     */
    public List<Carta> select_todos(String nome) {
		try {
			nome = "%" + nome + "%";
			List<Carta> cartas = new ArrayList<>();
			PreparedStatement ps;
			ps = connection.prepareStatement(SELECT_ALL);
			ps.setString(1, nome);
                        ps.setString(2, nome);
                        ps.setString(3, nome);
                        ps.setString(4, nome);
                        ps.setString(5, nome);
                        ps.setString(6, nome);
                        ps.setString(7, nome);
                        ps.setString(8, nome); 
                        ps.setString(9, nome);
                        ps.setString(10, nome);
                        ps.setString(11, nome);
                        ps.setString(12, nome);
                        ps.setString(13, nome);
                        ps.setString(14, nome);
			ResultSet result = ps.executeQuery();

			int i = 0;
			while (result.next()) {
				cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
						result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
						result.getString("raridade"), result.getString("qualidade")));
				cartas.get(i).setId(result.getInt("id"));
				cartas.get(i).setAno_venda(result.getString("ano_venda"));
				cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
				cartas.get(i).setQuantidade(result.getInt("quantidade"));                           
				

				cartas.get(i).setIcone(result.getBytes("imagem"));

				i++;

			}
                        ps.close();
                        connection.commit(); 
                        
			return cartas;

		} catch (SQLException e) {
			return null;
		}
	}

    /**
     *
     * @param nome
     * @return
     */
    public List<Carta> select_colecao_busca(String nome) {
		try {
			nome = nome + "%";
			List<Carta> cartas = new ArrayList<>();
			PreparedStatement ps;
			ps = connection.prepareStatement(SELECT_COLECAO_BUSCA);
			ps.setString(1, nome);
			ResultSet result = ps.executeQuery();

			int i = 0;
			while (result.next()) {
				cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
						result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
						result.getString("raridade"), result.getString("qualidade")));
				cartas.get(i).setId(result.getInt("id"));
				cartas.get(i).setAno_venda(result.getString("ano_venda"));
				cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
				cartas.get(i).setQuantidade(result.getInt("quantidade"));

				

				cartas.get(i).setIcone(result.getBytes("imagem"));

				i++;

			}
                        ps.close();
                        connection.commit(); 
                       
			return cartas;

		} catch (SQLException e) {
			return null;
		}
	}

    /**
     *
     * @param id
     * @return
     */
    public boolean vender(int id) {
		try {
			PreparedStatement ps = connection.prepareStatement(VENDA);
			ps.setInt(1, id);
			ps.execute();
                        ps.close();
                        connection.commit(); 
                        
			return true;

		} catch (SQLException e) {

			return false;
		}
	}

    /**
     *
     * @param carta
     * @param imagem
     * @return
     */
    public boolean inserir_venda(Carta carta, byte[] imagem) {
            
            
		try {
			PreparedStatement ps = connection.prepareStatement(INSERT_VENDA);
			ps.setString(1, carta.getNome_carta());
			ps.setString(2, carta.getCodigo());
			ps.setString(3, carta.getColecao());
			ps.setString(4, carta.getAno_compra());
			ps.setFloat(5, carta.getValor_pago());
			ps.setString(6, carta.getRaridade());
			ps.setString(7, carta.getQualidade());
			ps.setBytes(8, imagem);                        
			ps.setString(9, carta.getAno_venda());
			ps.setFloat(10, carta.getValor_venda());
			ps.execute();
                       
			ps.close();
                        connection.commit();
                        
			return true;

		} catch (SQLException e) {

			return false;
		}
	}

    /**
     *
     * @param carta
     * @param b
     * @return
     */
    public boolean inserir(Carta carta, byte[] b) {

		try {
			PreparedStatement ps = connection.prepareStatement(INSERT);
			ps.setString(1, carta.getNome_carta());
			ps.setString(2, carta.getCodigo());
			ps.setString(3, carta.getColecao());
			ps.setString(4, carta.getAno_compra());
			ps.setFloat(5, carta.getValor_pago());
			ps.setString(6, carta.getRaridade());
			ps.setString(7, carta.getQualidade());
			ps.setBytes(8, b);
			ps.executeUpdate();
                       
                        connection.commit();
                       
                        ps.close();
                        
                        
			return true;

		} catch (SQLException e) {
                    try {
                               connection.rollback(); // Em caso de erro, desfaz as mudanças
                           } catch (SQLException ex) {
                               ex.printStackTrace();
                           }
                           return false;
                } 
		
    }
	
    /**
     *
     * @param carta
     * @param b
     * @return
     */
    public boolean update(Carta carta, byte[] b) {
		// "UPDATE cartas SET nome_carta =?,
		// codigo=?,colecao=?,ano_compra=?,valor_pago=?,ano_venda=?,
		// valor_venda=?,raridade =?,quantidade=?,qualidade=? WHERE id = ?";
		if (b == null) {
			try {
				PreparedStatement ps = connection.prepareStatement(UPDATE_CARTA_SEM_FOTO);
				ps.setString(1, carta.getNome_carta());
				ps.setString(2, carta.getCodigo());
				ps.setString(3, carta.getColecao());
				ps.setString(4, carta.getAno_compra());
				ps.setFloat(5, carta.getValor_pago());
				ps.setString(6, carta.getAno_venda());
				ps.setFloat(7, carta.getValor_venda());
				ps.setString(8, carta.getRaridade());
				ps.setInt(9, carta.getQuantidade());
				ps.setString(10, carta.getQualidade());
				ps.setInt(11, carta.getId());

				ps.execute();
                               ps.close();
                        connection.commit();
                        
				return true;

			} catch (SQLException e) {

				try {
                               connection.rollback(); // Em caso de erro, desfaz as mudanças
                           } catch (SQLException ex) {
                               ex.printStackTrace();
                           }
                           return false;
                        } 
		} else {
			try {
				PreparedStatement ps = connection.prepareStatement(UPDATE_CARTA);
				ps.setString(1, carta.getNome_carta());
				ps.setString(2, carta.getCodigo());
				ps.setString(3, carta.getColecao());
				ps.setString(4, carta.getAno_compra());
				ps.setFloat(5, carta.getValor_pago());
				ps.setString(6, carta.getAno_venda());
				ps.setFloat(7, carta.getValor_venda());
				ps.setString(8, carta.getRaridade());
				ps.setInt(9, carta.getQuantidade());
				ps.setString(10, carta.getQualidade());
				ps.setBytes(11, b);
				ps.setInt(12, carta.getId());

				ps.execute();
                               ps.close();
                        connection.commit();
                        
				return true;

			} catch (SQLException e) {

                            try {
                               connection.rollback(); // Em caso de erro, desfaz as mudanças
                           } catch (SQLException ex) {
                               ex.printStackTrace();
                           }
                           return false;
                        } 
		}

	}

    /**
     *
     * @param id
     * @return
     */
    public boolean deletar(int id) {
		try {
			PreparedStatement ps = connection.prepareStatement(DELETE);
			ps.setInt(1, id);

			ps.execute();
                        ps.close();
                        connection.commit();
                        
			return true;

		} catch (SQLException e) {

			try {
                               connection.rollback(); // Em caso de erro, desfaz as mudanças
                           } catch (SQLException ex) {
                               ex.printStackTrace();
                           }
                           return false;
                        }
	}

    /**
     *
     * @param id
     * @return
     */
    public List<Carta> select_by_id(int id) {
		try {

			List<Carta> cartas = new ArrayList<>();
			PreparedStatement ps;
			ps = connection.prepareStatement(SELECT_BY_ID);
			ps.setInt(1, id);
			ResultSet result = ps.executeQuery();

			int i = 0;
			while (result.next()) {
				cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
						result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
						result.getString("raridade"), result.getString("qualidade")));
				cartas.get(i).setId(result.getInt("id"));
				cartas.get(i).setAno_venda(result.getString("ano_venda"));
				cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
				cartas.get(i).setQuantidade(result.getInt("quantidade"));

				

				cartas.get(i).setIcone(result.getBytes("imagem"));

				i++;

			}
                        ps.close();
                        connection.commit(); 
                        
			return cartas;

		} catch (SQLException e) {
			return null;
		}
	}
    

    /**
     *
     * @param integer
     * @return Retorna uma lista das cartas cadastradas
     * 
     */
    public List<Carta> select(int s) {
		if (s == 0) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(SELECT);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));			

					cartas.get(i).setIcone(result.getBytes("imagem"));
                                        
                                        //Blob blob = (Blob) result.getBlob("imagem");

					i++;

				}
                                ps.close();
                                connection.commit(); 
                                
				return cartas;

			} catch (SQLException e) {
                            return null;
			
                }
		} else if (s == 1) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(SELECT_BY_NAME);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                            ps.close();
                            connection.commit(); 
                            

				return cartas;

			} catch (SQLException e) {
                            return null;
			}
		} else if (s == 2) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(SELECT_BY_CODIGO);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					

                                        cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                                ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
                            return null;
			}
		} else if (s == 3) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(SELECT_BY_COLECAO);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					

                                        cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                                ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
				return null;
			}
		} else if (s == 4) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(SELECT_BY_ANO_COMPRA);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					

                                        cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                               ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
				return null;
			}
		} else if (s == 5) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(SELECT_BY_ANO_VENDA);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					

                                        cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                               ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (s == 6) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(SELECT_BY_VALOR_COMPRA);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					

                                        cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                                ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (s == 7) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(SELECT_BY_VALOR_VENDA);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));

					

                                        cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                                ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (s == 8) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(SELECT_BY_NAME_DESC);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					

                                        cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                                ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (s == 9) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(SELECT_BY_CODIGO_DESC);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					

				cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                                ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (s == 10) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(SELECT_BY_COLECAO_DESC);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					

				cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                                ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (s == 11) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(SELECT_BY_ANO_COMPRA_DESC);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					

				cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                                ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (s == 12) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(SELECT_BY_ANO_VENDA_DESC);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					

				cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                               ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (s == 13) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(SELECT_BY_VALOR_COMPRA_DESC);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					

				cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                                ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (s == 14) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(SELECT_BY_VALOR_VENDA_DESC);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					

				cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                                ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (s == 15) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(VENDIDAS);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					

				cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                               ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (s == 16) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(NAO_VENDIDAS);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					

				cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                                ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (s == 17) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(VENDIDAS_DESC);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					

				cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                                ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (s == 18) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(NAO_VENDIDAS_DESC);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					

				cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                                ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (s == 19) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(SELECT_QUANTIDADE);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					

				cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                                ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (s == 20) {
			try {

				List<Carta> cartas = new ArrayList<>();
				PreparedStatement ps;
				ps = connection.prepareStatement(SELECT_QUANTIDADE_DESC);
				ResultSet result = ps.executeQuery();

				int i = 0;
				while (result.next()) {
					cartas.add(new Carta(result.getString("nome_carta"), result.getString("codigo"),
							result.getString("colecao"), result.getString("ano_compra"), result.getFloat("valor_pago"),
							result.getString("raridade"), result.getString("qualidade")));
					cartas.get(i).setId(result.getInt("id"));
					cartas.get(i).setAno_venda(result.getString("ano_venda"));
					cartas.get(i).setValor_venda(result.getFloat("valor_venda"));
					cartas.get(i).setQuantidade(result.getInt("quantidade"));
					

				cartas.get(i).setIcone(result.getBytes("imagem"));
					i++;

				}
                                ps.close();
                            connection.commit(); 
                            
				return cartas;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	

}
