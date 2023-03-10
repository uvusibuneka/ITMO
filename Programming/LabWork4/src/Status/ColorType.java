package Status;

public enum ColorType {
        CLEAR("Чистый"),
        DIRTY("Грязный"),
        WHITE("Белый"),
        BLACK("Черный"),
        BLUE("Синий"),
        GREEN("Зелёный"),
        RED("Красный");

        private String replic;

        ColorType(String replic) {
            this.replic = replic;
        }

        @Override
        public String toString() {
            return replic;
        }
}

