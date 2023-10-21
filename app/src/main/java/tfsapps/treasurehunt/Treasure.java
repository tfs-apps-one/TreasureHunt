package tfsapps.treasurehunt;

public class Treasure {
    public double t_ido = 0.0f;
    public double t_kei = 0.0f;
    public int t_pos_x;
    public int t_pos_y;
    public boolean isAlive = false;
    public boolean isHit = false;
    public int t_type;  //宝物かガラクタ

    public Treasure(int x, int y, int type, double kei, double ido){
        t_pos_x = x;
        t_pos_y = y;
        t_kei = kei;
        t_ido = ido;
        t_type = type;
        isAlive = true;
    }


}
