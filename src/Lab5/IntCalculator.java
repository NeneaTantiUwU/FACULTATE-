package Lab5;

    public class IntCalculator {

        private int state;

        public IntCalculator(int initialValue) {
            this.state = initialValue;
        }

        public IntCalculator add(int value) {
            this.state += value;
            return this;
        }

        public IntCalculator subtract(int value) {
            this.state -= value;
            return this;
        }

        public IntCalculator multiply(int value) {
            this.state *= value;
            return this;
        }

        public int result() {
            return this.state;
        }

        public void clear() {
            this.state = 0;
        }
    }


