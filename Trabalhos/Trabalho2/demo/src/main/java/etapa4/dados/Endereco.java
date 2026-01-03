package etapa4.dados;

public class Endereco {
    private int idEndereco;
    private String cidade;
    private String rua;
    private int nroCasa;
    private String telefone;

    public Endereco() {}
    public Endereco(String cidade, String rua, int nroCasa, String telefone) {
        this.cidade = cidade;
        this.rua = rua;
        this.nroCasa = nroCasa;
        this.telefone = telefone;
    }

    public int getIdEndereco() { 
        return idEndereco; 
    }
    public void setIdEndereco(int idEndereco) { 
        this.idEndereco = idEndereco; 
    }
    public String getCidade() { 
        return cidade; 
    }
    public void setCidade(String cidade) { 
        this.cidade = cidade; 
    }
    public String getRua() { 
        return rua; 
    }
    public void setRua(String rua) { 
        this.rua = rua; 
    }
    public int getNroCasa() { 
        return nroCasa; 
    }
    public void setNroCasa(int nroCasa) { 
        this.nroCasa = nroCasa; 
    }
    public String getTelefone() { 
        return telefone; 
    }
    public void setTelefone(String telefone) { 
        this.telefone = telefone; 
    }

    @Override
    public String toString() {
        return "ID: " + idEndereco + "    |         " + cidade + 
        ",         " + rua + " nº  " + nroCasa + "         (" + telefone + ")";
    }
}
