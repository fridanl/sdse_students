package edu.sdse.csvprocessor;

public class CityRecord {
        private int id;
        private int year;
        private String city;
        private int population;

        public CityRecord(int id, int year, String city, int population) {
            this.id = id;
            this.year = year;
            this.city = city;
            this.population = population;
        }

        public int getId() {
            return id;
        }

        public int getYear() {
            return year;
        }

        public String getCity() {
            return city;
        }

        public int getPopulation() {
            return population;
        }

        @Override
        public String toString() {
            return "CityRecord{" +
                    "id=" + id +
                    ", year=" + year +
                    ", city='" + city + '\'' +
                    ", population=" + population +
                    '}';
        }
    }
