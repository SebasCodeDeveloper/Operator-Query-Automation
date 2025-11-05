package com.automatizacion;

import com.automatizacion.Controller.BusquedaContactos;
import com.automatizacion.Model.ExcelManager;
import com.automatizacion.View.ConsolaView;

public class App {
    public static void main(String[] args) {

        String rutaExcel = "C:/Users/sebat/OneDrive/Escritorio/Contactos.xlsx.xlsx"; // ← tu ruta

        ExcelManager excel = new ExcelManager();
        ConsolaView vista = new ConsolaView();
        BusquedaContactos controlador = new BusquedaContactos(excel, vista, rutaExcel);

        controlador.ejecutar();
    }
}
