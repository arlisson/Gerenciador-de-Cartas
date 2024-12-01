/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import DAO.CartaDAO;
import Model.Carta;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author T-GAMER
 */
public final class Tela extends javax.swing.JFrame {

    CartaDAO cartadao;
/**
     * Creates new form Tela
     */ 
    
        private int total_cartas = 0;
	private float total_gasto = 0;
	private float total_venda = 0;
        private int tamanho;
        private File imagemFile;
        MaskFormatter mask;
    
    
    public Tela() {
        /**
         * Criando instância para conectar com o BD
         */
        try{
          this.cartadao = CartaDAO.getInstance();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro:"+Arrays.toString(e.getStackTrace()),"Erro ao conectar com o banco",JOptionPane.ERROR_MESSAGE);
        }
       /**
        * Definindo máscara de formatação para o campo de ano da compra
        */
        try {
            mask = new MaskFormatter("####");
        } catch (ParseException ex) {
            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /**
         * Definindo estilo das telas
         */
        
        try {
    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
            UIManager.setLookAndFeel(info.getClassName());
            break;
        }
    }
} catch (Exception e) {
    e.printStackTrace();
}

        initComponents();  
        setLocationRelativeTo(null);
        
                /**
                 * Criando tabela
                 */
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Qualidade", "Quantidade", "id", "Nome", "C\u00F3digo", "Cole\u00E7\u00E3o", "Raridade",
						"Ano da Compra", "Valor Pago", "Ano venda", "Valor Venda", "Imagem" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(90);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(800);
		table.getColumnModel().getColumn(4).setPreferredWidth(270);
		table.getColumnModel().getColumn(5).setPreferredWidth(800);
		table.getColumnModel().getColumn(6).setPreferredWidth(270);
		table.getColumnModel().getColumn(7).setPreferredWidth(150);
		table.getColumnModel().getColumn(8).setPreferredWidth(150);
		table.getColumnModel().getColumn(9).setPreferredWidth(150);
		table.getColumnModel().getColumn(10).setPreferredWidth(150);
                
                DefaultTableModel modelo = (DefaultTableModel) table.getModel(); 
                TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
                table.setRowSorter(sorter);
                preenche_tabela(0, modelo, cartadao);
               
                /**
                 * Criando outra tabela
                 */
                table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_1.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Qualidade", "Quantidade", "id", "Nome", "C\u00F3digo", "Cole\u00E7\u00E3o", "Raridade",
						"Ano da Compra", "Valor Pago", "Ano venda", "Valor Venda", "imagem" }));
		table_1.getColumnModel().getColumn(0).setPreferredWidth(200);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(90);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(800);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(270);
		table_1.getColumnModel().getColumn(5).setPreferredWidth(800);
		table_1.getColumnModel().getColumn(6).setPreferredWidth(270);
		table_1.getColumnModel().getColumn(7).setPreferredWidth(150);
		table_1.getColumnModel().getColumn(8).setPreferredWidth(150);
		table_1.getColumnModel().getColumn(9).setPreferredWidth(150);
		table_1.getColumnModel().getColumn(10).setPreferredWidth(150);
                
                DefaultTableModel modelo_1 = (DefaultTableModel) table_1.getModel();    
                 
                preenche_tabela(0, modelo_1, cartadao);
                
                TableRowSorter<TableModel> sorter_1 = new TableRowSorter<>(table_1.getModel());
                table_1.setRowSorter(sorter_1);
                
                
                table.getColumnModel().removeColumn(table.getColumnModel().getColumn(11));
		table_1.getColumnModel().removeColumn(table_1.getColumnModel().getColumn(11));
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(2));
		table_1.getColumnModel().removeColumn(table_1.getColumnModel().getColumn(2));

                atualiza_dados();                
                
               
                
                /**
                 * Clique na linha da tabela para recuperar os dados dela
                 */
                table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Verifica se o botão esquerdo do mouse foi clicado
				if (SwingUtilities.isLeftMouseButton(e)) {
					int row = table_1.rowAtPoint(e.getPoint()); // Obtém a linha clicada
					TableModel model = table_1.getModel();
					if (row != -1) { // Verifica se a linha é válida
						// Obtém os dados da linha selecionada

						String qualidade = model.getValueAt(row, 0).toString();
						String quantidade = model.getValueAt(row, 1).toString();
						String id = model.getValueAt(row, 2).toString();
						String nomeCarta = model.getValueAt(row, 3).toString();
						String codigo = model.getValueAt(row, 4).toString();
						String colecao = model.getValueAt(row, 5).toString();
						String raridade = model.getValueAt(row, 6).toString();
						String ano_compra = model.getValueAt(row, 7).toString();
						String valor_pago = model.getValueAt(row, 8).toString();
						String ano_venda = model.getValueAt(row, 9).toString();
						String valor_venda = model.getValueAt(row, 10).toString();
                                                
						byte[] img =  (byte[]) model.getValueAt(row, 11);

						EditarCarta editarCarta = new EditarCarta();
                                             
                                               
                                           
						EditarCarta.imagem = img;
						EditarCarta.spinner_quantidade.setValue(Integer.valueOf(quantidade));
						EditarCarta.quantidade_atual = Integer.parseInt(quantidade);
						EditarCarta.qualidade = qualidade;
						EditarCarta.raridade = raridade;
                                                        
						EditarCarta.text_nome_carta.setText(nomeCarta);
						EditarCarta.text_valor_venda.setText(valor_venda);
						EditarCarta.text_codigo.setText(codigo);
						EditarCarta.text_id.setText(id);
						EditarCarta.text_valor_pago.setText(valor_pago);
						EditarCarta.text_ano_venda.setText(ano_venda);
						EditarCarta.text_data_compra.setText(ano_compra);
						EditarCarta.text_colecao.setText(colecao);
						EditarCarta.controle = 0;

						try {
                                                    // Recupera os bytes da imagem do modelo


                                                    // Verifique se o array de bytes não está vazio ou nulo
                                                    if (img != null && img.length > 0) {
                                                        // Converte os bytes em BufferedImage
                                                        BufferedImage imagem = ImageIO.read(new ByteArrayInputStream(img));

                                                        // Cria o ImageIcon a partir da BufferedImage
                                                        ImageIcon icone = new ImageIcon(imagem);

                                                        // Redimensiona a imagem para caber no JLabel
                                                        Image imagemRedimensionada = icone.getImage().getScaledInstance(200, 304, Image.SCALE_SMOOTH);

                                                        // Exibe a imagem no JLabel
                                                        EditarCarta.lbl_imagem.setIcon(new ImageIcon(imagemRedimensionada));
                                                    } else {
                                                        // Caso o BLOB esteja vazio ou nulo, talvez exiba uma imagem padrão ou um ícone de erro
                                                        EditarCarta.lbl_imagem.setIcon(null);
                                                    }
                                                } catch (IOException e1) {
                                                    // Trate as exceções
                                                    e1.printStackTrace();
                                                }

                                            // TODO Auto-generated catch block

						if (ano_venda.equals("Não vendida") && Integer.parseInt(quantidade) > 0) {
							EditarCarta.btn_venda.setEnabled(true);
							EditarCarta.btn_venda.setVisible(true);
							// EditarCarta.spinner_quantidade.setEnabled(true);

						} else {
							EditarCarta.btn_venda.setEnabled(false);
							EditarCarta.btn_venda.setVisible(false);
							// EditarCarta.spinner_quantidade.setEnabled(false);

						}

						dispose();
						//editarCarta.setExtendedState(JFrame.MAXIMIZED_BOTH);
						editarCarta.setVisible(true);

					}
				}
			}

		});
                /**
                 * Pegar os dados de uma carta, clicando em uma linha da tabela                 * 
                 */
                table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// Verifica se o botão esquerdo do mouse foi clicado
				if (SwingUtilities.isLeftMouseButton(e)) {
					int row = table.rowAtPoint(e.getPoint()); // Obtém a linha clicada
					TableModel model = table.getModel();
					if (row != -1) { // Verifica se a linha é válida
						// Obtém os dados da linha selecionada

						String qualidade = model.getValueAt(row, 0).toString();
						String quantidade = model.getValueAt(row, 1).toString();
						String id = model.getValueAt(row, 2).toString();
						String nomeCarta = model.getValueAt(row, 3).toString();
						String codigo = model.getValueAt(row, 4).toString();
						String colecao = model.getValueAt(row, 5).toString();
						String raridade = model.getValueAt(row, 6).toString();
						String ano_compra = model.getValueAt(row, 7).toString();
						String valor_pago = model.getValueAt(row, 8).toString();
						String ano_venda = model.getValueAt(row, 9).toString();
						String valor_venda = model.getValueAt(row, 10).toString();
						byte[] img =  (byte[]) model.getValueAt(row, 11);
						EditarCarta editarCarta = null;
                                                editarCarta = new EditarCarta();

						EditarCarta.imagem = img;

						EditarCarta.spinner_quantidade.setValue(Integer.valueOf(quantidade));
						EditarCarta.quantidade_atual = Integer.parseInt(quantidade);
						EditarCarta.qualidade = qualidade;
						EditarCarta.raridade = raridade;

						EditarCarta.text_nome_carta.setText(nomeCarta);
						EditarCarta.text_valor_venda.setText(valor_venda);
						EditarCarta.text_codigo.setText(codigo);
						EditarCarta.text_id.setText(id);
						EditarCarta.text_valor_pago.setText(valor_pago);
						EditarCarta.text_ano_venda.setText(ano_venda);
						EditarCarta.text_data_compra.setText(ano_compra);
						EditarCarta.text_colecao.setText(colecao);
						EditarCarta.controle = 0;
                                                
						if (ano_venda.equals("Não vendida") && Integer.parseInt(quantidade) > 0) {
							EditarCarta.btn_venda.setEnabled(true);
							EditarCarta.btn_venda.setVisible(true);
							// EditarCarta.spinner_quantidade.setEnabled(true);

						} else {
							EditarCarta.btn_venda.setEnabled(false);
							EditarCarta.btn_venda.setVisible(false);
							// EditarCarta.spinner_quantidade.setEnabled(false);
						}

						try {
                                                    // Recupera os bytes da imagem do modelo
                                                    //byte[] img = (byte[]) model.getValueAt(row, 11);

                                                    // Verifique se o array de bytes não está vazio ou nulo
                                                    if (img != null && img.length > 0) {
                                                        // Converte os bytes em BufferedImage
                                                        BufferedImage imagem = ImageIO.read(new ByteArrayInputStream(img));

                                                        // Cria o ImageIcon a partir da BufferedImage
                                                        ImageIcon icone = new ImageIcon(imagem);

                                                        // Redimensiona a imagem para caber no JLabel
                                                        Image imagemRedimensionada = icone.getImage().getScaledInstance(200, 304, Image.SCALE_SMOOTH);

                                                        // Exibe a imagem no JLabel
                                                        EditarCarta.lbl_imagem.setIcon(new ImageIcon(imagemRedimensionada));
                                                    } else {
                                                        // Caso o BLOB esteja vazio ou nulo, talvez exiba uma imagem padrão ou um ícone de erro
                                                        EditarCarta.lbl_imagem.setIcon(null);
                                                    }
                                                } catch (IOException e1) {
                                                    // Trate as exceções
                                                    e1.printStackTrace();
                                                }

                                            // TODO Auto-generated catch block

						dispose();
						//editarCarta.setExtendedState(JFrame.MAXIMIZED_BOTH);
						editarCarta.setVisible(true);

					}
				}
			}

		});
                
                
               
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel_Principal = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Panel_cadastrar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        text_nome_carta = new javax.swing.JTextField();
        lbl_imagem = new javax.swing.JLabel();
        cb_raridade = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cb_qualidade = new javax.swing.JComboBox<>();
        btn_cadastrar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        text_colecao = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        text_codigo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        text_valor_pago = new javax.swing.JTextField();
        lbl_total_cartas = new javax.swing.JLabel();
        lbl_total_venda = new javax.swing.JLabel();
        lbl_lucro = new javax.swing.JLabel();
        lbl_valor_gasto = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        cb_colecao = new javax.swing.JComboBox<>();
        text_data_compra = new javax.swing.JFormattedTextField();
        Panel_listar = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btn_limpar = new javax.swing.JButton();
        text_buscar_carta = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_1 = new javax.swing.JTable();
        lbl_total_cartas_1 = new javax.swing.JLabel();
        lbl_valor_gasto_1 = new javax.swing.JLabel();
        lbl_total_venda_1 = new javax.swing.JLabel();
        lbl_lucro_1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));

        Panel_Principal.setPreferredSize(new java.awt.Dimension(800, 600));

        Panel_cadastrar.setBorder(new javax.swing.border.MatteBorder(null));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("Cadastro de Cartas");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel2.setText("Nome da Carta");

        text_nome_carta.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        text_nome_carta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_nome_cartaActionPerformed(evt);
            }
        });

        lbl_imagem.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbl_imagem.setText("       Adicionar");
        lbl_imagem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbl_imagem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_imagem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_imagemMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_imagemMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_imagemMouseExited(evt);
            }
        });

        cb_raridade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NORMAL", "RARA", "SUPER RARA", "ULTRA RARA", "ULTIMATE RARA", "HOLOGRAPHIC RARE", "SECRETA RARA" }));
        cb_raridade.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                cb_raridadeAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        cb_raridade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_raridadeActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setText("Raridade");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setText("Qualidade");

        cb_qualidade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NOVA (M)", "QUASE NOVA (NM)", "LIGEIRAMENTE JOGADA (SP)", "MODERADAMENTE JOGADA (MP)", "JOGADA DEMAIS (HP)", "DANIFICADA (DM)" }));
        cb_qualidade.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                cb_qualidadeAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        btn_cadastrar.setBackground(new java.awt.Color(0, 153, 0));
        btn_cadastrar.setForeground(new java.awt.Color(255, 255, 255));
        btn_cadastrar.setText("Cadastrar");
        btn_cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cadastrarActionPerformed(evt);
            }
        });

        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setText("Coleção");

        text_colecao.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        text_colecao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_colecaoActionPerformed(evt);
            }
        });
        text_colecao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_colecaoKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel6.setText("Código");

        text_codigo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        text_codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_codigoActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel7.setText("Ano Compra");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel8.setText("Valor Pago");

        text_valor_pago.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        text_valor_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_valor_pagoActionPerformed(evt);
            }
        });

        lbl_total_cartas.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbl_total_cartas.setText("Total de Cartas:");

        lbl_total_venda.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbl_total_venda.setText("Valor Vendido:");

        lbl_lucro.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbl_lucro.setText("Lucro:");

        lbl_valor_gasto.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbl_valor_gasto.setText("Valor Gasto:");

        table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(table);

        cb_colecao.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        cb_colecao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome da Coleção" }));

        text_data_compra = new javax.swing.JFormattedTextField(mask);
        text_data_compra.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        javax.swing.GroupLayout Panel_cadastrarLayout = new javax.swing.GroupLayout(Panel_cadastrar);
        Panel_cadastrar.setLayout(Panel_cadastrarLayout);
        Panel_cadastrarLayout.setHorizontalGroup(
            Panel_cadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_cadastrarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_cadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(text_nome_carta)
                    .addGroup(Panel_cadastrarLayout.createSequentialGroup()
                        .addGroup(Panel_cadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(0, 455, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_cadastrarLayout.createSequentialGroup()
                        .addGroup(Panel_cadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(text_colecao, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Panel_cadastrarLayout.createSequentialGroup()
                                .addComponent(lbl_valor_gasto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_lucro))
                            .addGroup(Panel_cadastrarLayout.createSequentialGroup()
                                .addComponent(lbl_total_cartas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_total_venda))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Panel_cadastrarLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(text_codigo, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(18, 18, 18)
                        .addGroup(Panel_cadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Panel_cadastrarLayout.createSequentialGroup()
                                .addGroup(Panel_cadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(text_data_compra))
                                .addGap(18, 18, 18)
                                .addGroup(Panel_cadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                                    .addComponent(text_valor_pago)))
                            .addComponent(cb_colecao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_cadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(Panel_cadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(Panel_cadastrarLayout.createSequentialGroup()
                            .addComponent(btn_cadastrar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_cancelar))
                        .addComponent(cb_raridade, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_imagem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cb_qualidade, javax.swing.GroupLayout.Alignment.LEADING, 0, 200, Short.MAX_VALUE)))
                .addGap(14, 14, 14))
        );
        Panel_cadastrarLayout.setVerticalGroup(
            Panel_cadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_cadastrarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_cadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_cadastrarLayout.createSequentialGroup()
                        .addComponent(lbl_imagem, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cb_raridade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cb_qualidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(Panel_cadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_cadastrar)
                            .addComponent(btn_cancelar)))
                    .addGroup(Panel_cadastrarLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(text_nome_carta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(Panel_cadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(text_colecao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_colecao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_cadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Panel_cadastrarLayout.createSequentialGroup()
                                .addGroup(Panel_cadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(Panel_cadastrarLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(text_data_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(Panel_cadastrarLayout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(text_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(Panel_cadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_total_cartas)
                                    .addComponent(lbl_total_venda))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(Panel_cadastrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_lucro)
                                    .addComponent(lbl_valor_gasto))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(Panel_cadastrarLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(text_valor_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(24, 24, 24))
        );

        jTabbedPane1.addTab("Cadastrar Carta", Panel_cadastrar);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel9.setText("Listagem de Cartas");

        btn_limpar.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btn_limpar.setText("Limpar");
        btn_limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limparActionPerformed(evt);
            }
        });

        text_buscar_carta.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        text_buscar_carta.setToolTipText("");
        text_buscar_carta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_buscar_cartaKeyReleased(evt);
            }
        });

        table_1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(table_1);

        lbl_total_cartas_1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbl_total_cartas_1.setText("Total de Cartas:");

        lbl_valor_gasto_1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbl_valor_gasto_1.setText("Valor Gasto:");

        lbl_total_venda_1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbl_total_venda_1.setText("Valor Vendido:");

        lbl_lucro_1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbl_lucro_1.setText("Lucro:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel10.setText("Buscar");

        javax.swing.GroupLayout Panel_listarLayout = new javax.swing.GroupLayout(Panel_listar);
        Panel_listar.setLayout(Panel_listarLayout);
        Panel_listarLayout.setHorizontalGroup(
            Panel_listarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_listarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_listarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_listarLayout.createSequentialGroup()
                        .addGroup(Panel_listarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_listarLayout.createSequentialGroup()
                                .addGroup(Panel_listarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_total_cartas_1)
                                    .addComponent(lbl_valor_gasto_1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 331, Short.MAX_VALUE)
                                .addGroup(Panel_listarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_lucro_1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lbl_total_venda_1, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(Panel_listarLayout.createSequentialGroup()
                                .addGroup(Panel_listarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(240, 240, 240)
                        .addComponent(btn_limpar))
                    .addComponent(text_buscar_carta))
                .addContainerGap())
        );
        Panel_listarLayout.setVerticalGroup(
            Panel_listarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_listarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Panel_listarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(btn_limpar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(text_buscar_carta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(Panel_listarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_listarLayout.createSequentialGroup()
                        .addComponent(lbl_total_venda_1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_lucro_1))
                    .addGroup(Panel_listarLayout.createSequentialGroup()
                        .addComponent(lbl_total_cartas_1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_valor_gasto_1)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Listar Cartas", Panel_listar);

        javax.swing.GroupLayout Panel_PrincipalLayout = new javax.swing.GroupLayout(Panel_Principal);
        Panel_Principal.setLayout(Panel_PrincipalLayout);
        Panel_PrincipalLayout.setHorizontalGroup(
            Panel_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        Panel_PrincipalLayout.setVerticalGroup(
            Panel_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Tabbed_pane");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel_Principal, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel_Principal, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
public void preenche_tabela(int i, DefaultTableModel modelo, CartaDAO cartadao) {    
               
    
		modelo.setNumRows(0);
                
                if(cartadao.select(i)==null){
                    
                }else{
                    for (Carta c : cartadao.select(i)) {

			modelo.addRow(new Object[] { c.getQualidade(), c.getQuantidade(), c.getId(), c.getNome_carta(),
					c.getCodigo(), c.getColecao(), c.getRaridade(), c.getAno_compra(), "R$" + c.getValor_pago(),
					c.getAno_venda(), "R$" + c.getValor_venda(), c.getIcone() });
                
                

                        }
                }
		
             

	}
/**
 * 
 * @param file
 * @return ByteArray
 * @throws IOException Exceção de carregamento de arquivos
 * 
 * @see Função para carregar um arquivo de imagem, e transforma-lo em um array de bytes
 * 
 */


private static byte[] carregarImagem(File file) throws IOException {
        byte[] bytesArray = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(bytesArray);
        }
      
        return bytesArray;
    }
/**
 * Função para atualizar os dados sobre a coleção de cartas
 */
private void atualiza_dados(){
    total_cartas = 0;
    total_gasto = 0;
    total_venda=0;
    if(cartadao.select(0)!=null){
                    for (Carta c : cartadao.select(0)) {
			total_cartas += c.getQuantidade();
			total_gasto += c.getValor_pago() * c.getQuantidade();
			total_venda += c.getValor_venda();
                    }
                }
    
    lbl_total_cartas.setText("Total de Cartas: " + total_cartas);
    lbl_total_venda.setText("Valor vendido: R$" + String.format("%.2f", total_venda));
    lbl_valor_gasto.setText("Valor Gasto: R$" + String.format("%.2f", total_gasto));
    lbl_lucro.setText("Lucro: R$" + String.format("%.2f", (total_venda - total_gasto)));
    lbl_total_cartas_1.setText("Total de Cartas: " + total_cartas);
    lbl_total_venda_1.setText("Valor vendido: R$" + String.format("%.2f", total_venda));
    lbl_valor_gasto_1.setText("Valor Gasto: R$" + String.format("%.2f", total_gasto));
    lbl_lucro_1.setText("Lucro: R$" + String.format("%.2f", (total_venda - total_gasto)));
    
    
}
   
    
    
     
    private void text_nome_cartaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_nome_cartaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_nome_cartaActionPerformed

    private void text_colecaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_colecaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_colecaoActionPerformed

    private void text_codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_codigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_codigoActionPerformed

    private void text_valor_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_valor_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_valor_pagoActionPerformed

    private void btn_cadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadastrarActionPerformed
                                 DefaultTableModel modelo = (DefaultTableModel) table.getModel();
                                 DefaultTableModel modelo_1 = (DefaultTableModel) table_1.getModel();
				if (!text_nome_carta.getText().isEmpty()) {
					if (!text_colecao.getText().isEmpty()) {
						if (!text_codigo.getText().isEmpty()) {
							if (!text_data_compra.getText().isEmpty()) {
								if (!text_valor_pago.getText().isEmpty()) {
									if (tamanho != 0) {
										if (cb_colecao.getSelectedIndex() == 0) {
                                                                                    try {
                                                                                        if (cartadao.inserir(new Carta(text_nome_carta.getText().toUpperCase(),
                                                                                                text_codigo.getText().toUpperCase(),
                                                                                                text_colecao.getText().toUpperCase(), text_data_compra.getText(),
                                                                                                Float.parseFloat(text_valor_pago.getText()),
                                                                                                cb_raridade.getSelectedItem().toString().toUpperCase(),
                                                                                                cb_qualidade.getSelectedItem().toString().toUpperCase()),carregarImagem(imagemFile))) {
                                                                                            
                                                                                            text_codigo.setText(null);
                                                                                            // text_colecao.setText(null);
                                                                                            text_data_compra.setText(null);
                                                                                            text_nome_carta.setText(null);
                                                                                            text_valor_pago.setText(null);
                                                                                            lbl_imagem.setIcon(null);
                                                                                            lbl_imagem.setText("       Adicionar");
                                                                                            lbl_imagem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                                                                                            cb_qualidade.setSelectedIndex(0);
                                                                                            cb_raridade.setSelectedIndex(0);
                                                                                            
                                                                                            JOptionPane.showMessageDialog(null, "Carta Cadastrada com sucesso!",
                                                                                                    "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                                                                                            
                                                                                            
                                                                                            preenche_tabela(0, modelo, cartadao);
                                                                                            preenche_tabela(0, modelo_1, cartadao);
                                                                                            atualiza_dados();
                                                                                            lbl_total_cartas.setText("Total de Cartas: " + total_cartas);
                                                                                            lbl_valor_gasto
                                                                                                    .setText(String.valueOf("Valor Gasto: R$" + total_gasto));
                                                                                            lbl_total_venda
                                                                                                    .setText(String.valueOf("Valor vendido: R$" + total_venda));
                                                                                            lbl_lucro.setText(
                                                                                                    "Lucro: R$" + String.valueOf(total_venda - total_gasto));
                                                                                            
                                                                                        }
                                                                                    } catch (IOException ex) {
                                                                                        Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                                                                                    }
										} else {
                                                                                    try {
                                                                                        if (cartadao.inserir(
                                                                                                new Carta(text_nome_carta.getText().toUpperCase(),
                                                                                                        text_codigo.getText().toUpperCase(),
                                                                                                        cb_colecao.getSelectedItem().toString().toUpperCase(),
                                                                                                        text_data_compra.getText(),
                                                                                                        Float.parseFloat(text_valor_pago.getText()),
                                                                                                        cb_raridade.getSelectedItem().toString().toUpperCase(),
                                                                                                        cb_qualidade.getSelectedItem().toString().toUpperCase()),carregarImagem(imagemFile))) {
                                                                                            
                                                                                            text_codigo.setText(null);
                                                                                            // text_colecao.setText(null);
                                                                                            text_data_compra.setText(null);
                                                                                            text_nome_carta.setText(null);
                                                                                            text_valor_pago.setText(null);
                                                                                            lbl_imagem.setIcon(null);
                                                                                            cb_qualidade.setSelectedIndex(0);
                                                                                            cb_raridade.setSelectedIndex(0);
                                                                                            lbl_imagem.setIcon(null);
                                                                                            lbl_imagem.setText("       Adicionar");
                                                                                            lbl_imagem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                                                                                            
                                                                                            
                                                                                            JOptionPane.showMessageDialog(null, "Carta Cadastrada com sucesso!",
                                                                                                    "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                                                                                            modelo.setNumRows(0);
                                                                                            total_cartas = 0;
                                                                                            total_gasto = 0;
                                                                                            for (Carta c : cartadao.select(0)) {
                                                                                                modelo.addRow(new Object[] { c.getQualidade(), c.getQuantidade(),
                                                                                                    c.getId(), c.getNome_carta(), c.getCodigo(), c.getColecao(),
                                                                                                    c.getRaridade(), c.getAno_compra(),
                                                                                                    "R$" + c.getValor_pago(), c.getAno_venda(),
                                                                                                    "R$" + c.getValor_venda(), c.getIcone() });
                                                                                                
                                                                                                                                                                                              
                                                                                            }
                                                                                            
                                                                                            atualiza_dados();
                                                                                            
                                                                                            lbl_total_cartas.setText("Total de Cartas: " + total_cartas);
                                                                                            lbl_valor_gasto
                                                                                                    .setText(String.valueOf("Valor Gasto: R$" + total_gasto));
                                                                                            lbl_total_venda
                                                                                                    .setText(String.valueOf("Valor vendido: R$" + total_venda));
                                                                                            lbl_lucro.setText(
                                                                                                    "Lucro: R$" + String.valueOf(total_venda - total_gasto));
                                                                                            
                                                                                        }else{
                                                                                            JOptionPane.showMessageDialog(null,"Erro ao inserir carta!","Erro",JOptionPane.ERROR_MESSAGE);
                                                                                        }
                                                                                    } catch (IOException ex) {
                                                                                        Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                                                                                    }
										}

									} else {
										lbl_imagem.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
										JOptionPane.showMessageDialog(null, "Por favor, selecione uma imagem!",
												"Atenção!", JOptionPane.WARNING_MESSAGE);
										lbl_imagem.setBorder(UIManager.getBorder("TextField.border"));
									}
								} else {
									text_valor_pago.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
									JOptionPane.showMessageDialog(null, "Por favor, insira o valor pago na carta!",
											"Atenção", JOptionPane.WARNING_MESSAGE);
									text_valor_pago.setBorder(UIManager.getBorder("TextField.border"));
								}
							} else {
								text_data_compra.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
								JOptionPane.showMessageDialog(null, "Por favor, insira a data da compra da carta!",
										"Atenção!", JOptionPane.WARNING_MESSAGE);
								text_data_compra.setBorder(UIManager.getBorder("TextField.border"));
							}

						} else {
							text_codigo.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
							JOptionPane.showMessageDialog(null, "Por favor, insira o código da carta!", "Atenção",
									JOptionPane.WARNING_MESSAGE);
							text_codigo.setBorder(UIManager.getBorder("TextField.border"));
						}
					} else {
						text_colecao.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
						JOptionPane.showMessageDialog(null, "Por favor, insira o nome da coleção da carta!", "Atenção!",
								JOptionPane.WARNING_MESSAGE);
						text_colecao.setBorder(UIManager.getBorder("TextField.border"));
					}

				} else {
					text_nome_carta.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					JOptionPane.showMessageDialog(null, "Por favor, insira o nome da carta!", "Atenção!",
							JOptionPane.WARNING_MESSAGE);
					text_nome_carta.setBorder(UIManager.getBorder("TextField.border"));
				}

    }//GEN-LAST:event_btn_cadastrarActionPerformed

    private void lbl_imagemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_imagemMouseClicked
        
				JFileChooser jfc = new JFileChooser(
						"");
				jfc.setDialogTitle("Selecionar arquivo");

				int resultado = jfc.showOpenDialog(jfc);
                                    
				if (resultado == JFileChooser.APPROVE_OPTION) {
					try {
                                            
                                            new FileInputStream(jfc.getSelectedFile());
                                                imagemFile =  new File(jfc.getSelectedFile().getAbsolutePath());
						tamanho = (int) jfc.getSelectedFile().length();
						Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lbl_imagem.getWidth(),
								lbl_imagem.getHeight(), Image.SCALE_SMOOTH);
						lbl_imagem.setIcon(new ImageIcon(foto));
                                                lbl_imagem.setText("");
                                                lbl_imagem.setBorder(javax.swing.BorderFactory.createEmptyBorder(1,1,1,1));

						lbl_imagem.updateUI();

					} catch (IOException e1) {
						System.out.println(e1);
					}
				}
    }//GEN-LAST:event_lbl_imagemMouseClicked

    private void text_colecaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_colecaoKeyReleased
StringBuilder colecoes = new StringBuilder();
				if (!text_colecao.getText().isEmpty()) {
					cb_colecao.removeAllItems();
					cb_colecao.addItem("Nome da Coleção");
					// Concatena as coleções retornadas em uma String
                                        if(cartadao.select_colecao_busca(text_colecao.getText())!=null){
                                            for (Carta c : cartadao.select_colecao_busca(text_colecao.getText())) {
						colecoes.append(c.getColecao()).append("\n");
						cb_colecao.addItem(c.getColecao());
                                            } 
                                        }
					

					// Se houver resultados, exibe no JOptionPane

				} else {
					cb_colecao.removeAllItems();
					cb_colecao.addItem("Nome da Coleção");
				}        // TODO add your handling code here:
    }//GEN-LAST:event_text_colecaoKeyReleased

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
                                text_codigo.setText(null);
				text_colecao.setText(null);
				text_data_compra.setText(null);
				text_nome_carta.setText(null);
				text_valor_pago.setText(null);
				lbl_imagem.setIcon(null);
                                lbl_imagem.setText("       Adicionar");
                                lbl_imagem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

				cb_colecao.setSelectedIndex(0);
                                // TODO add your handling code here:
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparActionPerformed
                            DefaultTableModel modelo_1 = (DefaultTableModel) table_1.getModel();
				text_buscar_carta.setText(null);
				
                                preenche_tabela(0, modelo_1, cartadao);// TODO add your handling code here:
    }//GEN-LAST:event_btn_limparActionPerformed

    private void lbl_imagemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_imagemMouseEntered
       if(lbl_imagem.getIcon()==null){
           lbl_imagem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
       }else{
           lbl_imagem.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

       }
    }//GEN-LAST:event_lbl_imagemMouseEntered

    private void lbl_imagemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_imagemMouseExited
       if(lbl_imagem.getIcon()==null){
           lbl_imagem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

       }else{
           lbl_imagem.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

       }
    }//GEN-LAST:event_lbl_imagemMouseExited

    private void text_buscar_cartaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_buscar_cartaKeyReleased
       DefaultTableModel modelo_1 = (DefaultTableModel) table_1.getModel();
              modelo_1.setNumRows(0);
              if(cartadao.select_todos(text_buscar_carta.getText())!=null){
                  for(Carta c: cartadao.select_todos(text_buscar_carta.getText())){
                  modelo_1.addRow(new Object[] { c.getQualidade(), c.getQuantidade(), c.getId(), c.getNome_carta(),
					c.getCodigo(), c.getColecao(), c.getRaridade(), c.getAno_compra(), "R$" + c.getValor_pago(),
					c.getAno_venda(), "R$" + c.getValor_venda(), c.getIcone() });

              }
              }
              
        if(text_buscar_carta.getText().isEmpty()){
            preenche_tabela(0, modelo_1, cartadao);
        }
    }//GEN-LAST:event_text_buscar_cartaKeyReleased

    private void cb_raridadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_raridadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_raridadeActionPerformed

    private void cb_raridadeAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_cb_raridadeAncestorAdded
        // TODO add your handling code here:
        List<String> raridade = cartadao.raridade();
            if(raridade!=null){
                // Limpar Combo Box
				cb_raridade.removeAllItems();			

				for (String r : raridade) {
					cb_raridade.addItem(r);
				}
            }
				

				cb_raridade.setSelectedIndex(0);
        
        
    }//GEN-LAST:event_cb_raridadeAncestorAdded

    private void cb_qualidadeAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_cb_qualidadeAncestorAdded
        // TODO add your handling code here:
        List<String> qualidade = cartadao.qualidade();
            if(qualidade !=null){
                // Limpar Combo Box
				cb_qualidade.removeAllItems();			

				for (String q : qualidade) {
					cb_qualidade.addItem(q);
				}
            }
				

				cb_qualidade.setSelectedIndex(0);
                                
    }//GEN-LAST:event_cb_qualidadeAncestorAdded

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
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Tela().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel_Principal;
    private javax.swing.JPanel Panel_cadastrar;
    private javax.swing.JPanel Panel_listar;
    private javax.swing.JButton btn_cadastrar;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_limpar;
    private javax.swing.JComboBox<String> cb_colecao;
    private javax.swing.JComboBox<String> cb_qualidade;
    private javax.swing.JComboBox<String> cb_raridade;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbl_imagem;
    private javax.swing.JLabel lbl_lucro;
    private javax.swing.JLabel lbl_lucro_1;
    private javax.swing.JLabel lbl_total_cartas;
    private javax.swing.JLabel lbl_total_cartas_1;
    private javax.swing.JLabel lbl_total_venda;
    private javax.swing.JLabel lbl_total_venda_1;
    private javax.swing.JLabel lbl_valor_gasto;
    private javax.swing.JLabel lbl_valor_gasto_1;
    private javax.swing.JTable table;
    private javax.swing.JTable table_1;
    private javax.swing.JTextField text_buscar_carta;
    private javax.swing.JTextField text_codigo;
    private javax.swing.JTextField text_colecao;
    private javax.swing.JFormattedTextField text_data_compra;
    private javax.swing.JTextField text_nome_carta;
    private javax.swing.JTextField text_valor_pago;
    // End of variables declaration//GEN-END:variables
}
