/**
 * Simplest code ever, read if button pressed -> set pin state to outputpin (which controls the mosfet)
 */

const int switchPin = 2;
const int outputPin = 9;
int switchState = 0;

void setup() {
  // put your setup code here, to run once:
  pinMode(outputPin, OUTPUT);
  pinMode(switchPin, INPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  switchState = digitalRead(switchPin);
  int outputState = (switchState == HIGH) ? HIGH : LOW;
  digitalWrite(outputPin, outputState);
  
}
