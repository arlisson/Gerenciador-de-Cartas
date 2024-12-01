Este é um arquivo com uma breve explicação sobre a versão 2.0 do Software de Gestão de YU-GI-OH!

Alterações no código fonte:

Houve uma migração de bancos de dados, o projeto inicial utilizava o XAMPP como servidor para acessar o banco MySQL,
agora o sistema utiliza o banco de dados local sqlite.

A principal mudança no código referente a isso é a alteração do campo LONGBLOB, nativo do mysql, para BLOB, nativo do sqlite
que não possui LONGBLOB.

Por conta dessa mudança, o código precisou ser alterado, os campos que utilizavam o BLOB, da biblioteca do mysql, agora
utilizam byte[], um Array de bytes, para armazenar e exibir as imagens.

Alterações no build.xml:

Por conta do banco sqlite ser local, é necessário extrai-lo junto com o projeto quando o jar é criado, utilziando o seguinte código:

<copy todir="${store.dir}/diretório/onde/o/seu/banco/está/no/projeto">
        <fileset dir="nome/da/pasta/de/destino"/>
    </copy>


