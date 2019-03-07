package ase;

import ase.Stage1.Stage1;
import ase.Stage2.Stage2;
import ase.Stage3.Stage3;
import ase.Stage4.Stage4;

public class Program {
    public static void main(String[] args) {

        Stage1 stage1 = new Stage1();
        stage1.run();

        Stage2 stage2 = new Stage2();
        stage2.run();

        Stage3 stage3 = new Stage3();
        stage3.run();

        Stage4 stage4 = new Stage4();
        stage4.run();
    }
}
