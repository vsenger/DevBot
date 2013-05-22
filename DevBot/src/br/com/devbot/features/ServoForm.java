package br.com.devbot.features;

import br.com.devbot.core.MainForm;
import com.sun.lwuit.Slider;
import com.sun.lwuit.events.DataChangedListener;
import com.sun.lwuit.layouts.BorderLayout;

public class ServoForm extends FeatureForm {
    
    private ServoSlider servoSlider;
    
    public ServoForm(MainForm previousForm) {
        super(previousForm, "Servo motor");
        setHeaderCode("servo?");
        this.setLayout(new BorderLayout());
        servoSlider = new ServoSlider();
        this.addComponent(BorderLayout.CENTER, servoSlider);
    }
    
    class ServoSlider extends Slider {
        
        private final int MIN = 0;
        private final int MAX = 180;
        private final int MINUS = -3;
        private final int PLUS = -4;
        private final int DEFAULT = 90;
        private int actualAngle;
        
        public ServoSlider() {
            super();
            this.setMinValue(MIN);
            this.setMaxValue(MAX);
            this.setEditable(true);
            actualAngle = DEFAULT;
            this.setProgress(actualAngle);
        }
        
        public void keyPressed(int keyCode) {
            switch (keyCode) {
                case MINUS:
                    actualAngle -= 10;
                    if (actualAngle < 0) {
                        actualAngle = 0;
                    }
                    setProgress(actualAngle);
                    sendMessage(""+actualAngle);
                    break;
                case PLUS:
                    actualAngle += 10;
                    if (actualAngle > 179) {
                        actualAngle = 179;
                    }
                    this.setProgress(actualAngle);
                    sendMessage(""+actualAngle);
                    break;
            }
        }
        
        public int getActualSpeed() {
            return actualAngle;
        }
    }
}
