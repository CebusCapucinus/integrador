package com.mycompany.integrador;

import com.mycompany.integrador.entities.Categoria;
import com.mycompany.integrador.entities.Pedido;
import com.mycompany.integrador.entities.Producto;
import com.mycompany.integrador.entities.Usuario;
import com.mycompany.integrador.enums.Estado;
import com.mycompany.integrador.enums.FormaPago;
import com.mycompany.integrador.enums.Rol;
import com.mycompany.integrador.services.CategoryService;
import com.mycompany.integrador.services.OrderService;
import com.mycompany.integrador.services.ProductService;
import com.mycompany.integrador.services.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static CategoryService categoryService = new CategoryService();
    private static ProductService productService = new ProductService(categoryService);
    private static UserService userService = new UserService();
    private static OrderService orderService = new OrderService(userService, productService);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n=== FOOD STORE SYSTEM ===");
            System.out.println("1. Categorias");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");
            int option = readInteger();
            switch (option) {
                case 1:
                    categoriesMenu();
                    break;
                case 2:
                    productsMenu();
                    break;
                case 3:
                    usersMenu();
                    break;
                case 4:
                    menuPedidos();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opcion invalida");
            }
        }

    }

    private static void categoriesMenu() {
        while (true) {
            System.out.println("\n--- CATEGORIAS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            int op = readInteger();
            try {
                switch (op) {
                    case 1:
                        List<Categoria> cats = categoryService.listar();
                        if (cats.isEmpty()) {
                            System.out.println("No hay categorias cargadas");
                        } else {
                            cats.forEach(System.out::println);
                        }
                        break;
                    case 2:
                        System.out.print("Nombre: ");
                        String nom = scanner.nextLine();
                        System.out.print("Descripcion: ");
                        String desc = scanner.nextLine();
                        Categoria c = categoryService.crear(nom, desc);
                        System.out.println("Categoria creada con ID: " + c.getId());
                        break;
                    case 3:
                        System.out.print("Id de categoria a editar: ");
                        Long id = readLong();
                        System.out.print("Nuevo nombre (dejar vacio para no cambiar): ");
                        String newNom = scanner.nextLine();
                        if (newNom.trim().isEmpty()) {
                            newNom = null;
                        }
                        System.out.print("Nueva descripcion (dejar vacio para no cambiar): ");
                        String newDesc = scanner.nextLine();
                        if (newDesc.trim().isEmpty()) {
                            newDesc = null;
                        }
                        categoryService.editar(id, newNom, newDesc);
                        System.out.println("Categoria actualizada");
                        break;
                    case 4:
                        System.out.print("Id de categoria a eliminar: ");
                        Long idDel = readLong();
                        System.out.print("Confirmar (S/N): ");
                        String confirm = scanner.nextLine();
                        if (confirm.equalsIgnoreCase("S")) {
                            categoryService.eliminar(idDel);
                            System.out.println("Categoria eliminada");
                        } else {
                            System.out.println("Operacion cancelada");
                        }
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Opcion invalida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void productsMenu() {
        while (true) {
            System.out.println("\n--- PRODUCTOS ---");
            System.out.println("1. Listar todos");
            System.out.println("2. Listar por categoria");
            System.out.println("3. Crear");
            System.out.println("4. Editar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            int op = readInteger();
            try {
                switch (op) {
                    case 1:
                        List<Producto> prods = productService.listar();
                        if (prods.isEmpty()) {
                            System.out.println("No hay productos");
                        } else {
                            prods.forEach(System.out::println);
                        }
                        break;
                    case 2:
                        System.out.print("Id de categoria: ");
                        Long catId = readLong();
                        List<Producto> porCat = productService.listByCategory(catId);
                        if (porCat.isEmpty()) {
                            System.out.println("No hay productos en esa categoria");
                        } else {
                            porCat.forEach(System.out::println);
                        }
                        break;
                    case 3:
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Descripcion: ");
                        String descrip = scanner.nextLine();
                        System.out.print("Precio: ");
                        double precio = readDouble();
                        System.out.print("Stock: ");
                        int stock = readInteger();
                        System.out.print("Imagen URL: ");
                        String imagen = scanner.nextLine();
                        System.out.print("Disponible (true/false): ");
                        boolean disponible = Boolean.parseBoolean(scanner.nextLine());
                        System.out.print("Id de categoria: ");
                        Long catIdC = readLong();
                        Producto p = productService.crear(nombre, descrip, precio, stock, imagen, disponible, catIdC);
                        System.out.println("Producto creado con Id: " + p.getId());
                        break;
                    case 4:
                        System.out.print("Id del producto: ");
                        Long prodId = readLong();
                        System.out.print("Nuevo nombre (dejar vacio): ");
                        String nNom = scanner.nextLine();
                        if (nNom.trim().isEmpty()) {
                            nNom = null;
                        }
                        System.out.print("Nueva descripcion: ");
                        String nDesc = scanner.nextLine();
                        if (nDesc.trim().isEmpty()) {
                            nDesc = null;
                        }
                        System.out.print("Nuevo precio (dejar -1 para no cambiar): ");
                        double nPrecio = readDouble();
                        Double precioObj = (nPrecio < 0) ? null : nPrecio;
                        System.out.print("Nuevo stock (dejar -1): ");
                        int nStock = readInteger();
                        Integer stockObj = (nStock < 0) ? null : nStock;
                        System.out.print("Nueva imagen: ");
                        String nImg = scanner.nextLine();
                        if (nImg.trim().isEmpty()) {
                            nImg = null;
                        }
                        System.out.print("Disponible (true/false, dejar vacio): ");
                        String dispStr = scanner.nextLine();
                        Boolean dispObj = dispStr.trim().isEmpty() ? null : Boolean.parseBoolean(dispStr);
                        System.out.print("Nuevo Id categoria (dejar 0): ");
                        Long catIdE = readLong();
                        Long catObj = (catIdE == 0) ? null : catIdE;
                        productService.editar(prodId, nNom, nDesc, precioObj, stockObj, nImg, dispObj, catObj);
                        System.out.println("Producto actualizado");
                        break;
                    case 5:
                        System.out.print("Id del producto a eliminar: ");
                        Long prodDel = readLong();
                        System.out.print("Confirmar (S/N): ");
                        String conf = scanner.nextLine();
                        if (conf.equalsIgnoreCase("S")) {
                            productService.eliminar(prodDel);
                            System.out.println("Producto eliminado");
                        } else {
                            System.out.println("Cancelado");
                        }
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Opcion invalida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void usersMenu() {
        while (true) {
            System.out.println("\n--- USUARIOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            int op = readInteger();
            try {
                switch (op) {
                    case 1:
                        List<Usuario> users = userService.listar();
                        if (users.isEmpty()) {
                            System.out.println("No hay usuarios");
                        } else {
                            users.forEach(System.out::println);
                        }
                        break;
                    case 2:
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Apellido: ");
                        String apellido = scanner.nextLine();
                        System.out.print("Mail: ");
                        String mail = scanner.nextLine();
                        System.out.print("Celular: ");
                        String celular = scanner.nextLine();
                        System.out.print("Contrasena: ");
                        String pass = scanner.nextLine();
                        System.out.print("Rol (ADMIN/USUARIO): ");
                        String rolStr = scanner.nextLine();
                        Rol rol = Rol.valueOf(rolStr.toUpperCase());
                        Usuario u = userService.crear(nombre, apellido, mail, celular, pass, rol);
                        System.out.println("Usuario creado con Id: " + u.getId());
                        break;
                    case 3:
                        System.out.print("Id del usuario: ");
                        Long id = readLong();
                        System.out.print("Nuevo nombre (dejar vacio): ");
                        String nNom = scanner.nextLine();
                        if (nNom.trim().isEmpty()) {
                            nNom = null;
                        }
                        System.out.print("Nuevo apellido: ");
                        String nApe = scanner.nextLine();
                        if (nApe.trim().isEmpty()) {
                            nApe = null;
                        }
                        System.out.print("Nuevo mail: ");
                        String nMail = scanner.nextLine();
                        if (nMail.trim().isEmpty()) {
                            nMail = null;
                        }
                        System.out.print("Nuevo celular: ");
                        String nCel = scanner.nextLine();
                        if (nCel.trim().isEmpty()) {
                            nCel = null;
                        }
                        System.out.print("Nueva contrasena: ");
                        String nPass = scanner.nextLine();
                        if (nPass.trim().isEmpty()) {
                            nPass = null;
                        }
                        System.out.print("Nuevo rol (ADMIN/USUARIO, dejar vacio): ");
                        String nRol = scanner.nextLine();
                        Rol rolObj = nRol.trim().isEmpty() ? null : Rol.valueOf(nRol.toUpperCase());
                        userService.editar(id, nNom, nApe, nMail, nCel, nPass, rolObj);
                        System.out.println("Usuario actualizado");
                        break;
                    case 4:
                        System.out.print("Id del usuario a eliminar: ");
                        Long idDel = readLong();
                        System.out.print("Confirmar (S/N): ");
                        String conf = scanner.nextLine();
                        if (conf.equalsIgnoreCase("S")) {
                            userService.eliminar(idDel);
                            System.out.println("Usuario eliminado logicamente");
                        } else {
                            System.out.println("Cancelado");
                        }
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Opcion invalida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void menuPedidos() {
        while (true) {
            System.out.println("\n--- PEDIDOS ---");
            System.out.println("1. Listar todos");
            System.out.println("2. Listar por usuario");
            System.out.println("3. Crear pedido");
            System.out.println("4. Actualizar estado/forma de pago");
            System.out.println("5. Eliminar pedido");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            int op = readInteger();
            try {
                switch (op) {
                    case 1:
                        List<Pedido> pedidos = orderService.listar();
                        if (pedidos.isEmpty()) {
                            System.out.println("No hay pedidos");
                        } else {
                            pedidos.forEach(System.out::println);
                        }
                        break;
                    case 2:
                        System.out.print("Id de usuario: ");
                        Long uid = readLong();
                        List<Pedido> pedidosUser = orderService.listarPorUsuario(uid);
                        if (pedidosUser.isEmpty()) {
                            System.out.println("No hay pedidos para ese usuario");
                        } else {
                            pedidosUser.forEach(System.out::println);
                        }
                        break;
                    case 3:
                        System.out.print("Id de usuario: ");
                        Long uidPed = readLong();
                        System.out.println("Formas de pago: TARJETA, TRANSFERENCIA, EFECTIVO");
                        System.out.print("Seleccione: ");
                        String fpStr = scanner.nextLine();
                        FormaPago fp = FormaPago.valueOf(fpStr.toUpperCase());
                        Map<Producto, Integer> detalles = new HashMap<>();
                        while (true) {
                            System.out.print("ID del producto (0 para terminar): ");
                            Long prodId = readLong();
                            if (prodId == 0) {
                                break;
                            }
                            Producto prod = productService.buscarPorId(prodId);
                            System.out.print("Cantidad: ");
                            int cant = readInteger();
                            detalles.put(prod, cant);
                        }
                        if (detalles.isEmpty()) {
                            System.out.println("No se puede crear un pedido sin detalles");
                            break;
                        }
                        Pedido nuevo = orderService.crear(uidPed, fp, detalles);
                        System.out.println("Pedido creado con Id: " + nuevo.getId() + " - Total: " + nuevo.getTotal());
                        break;
                    case 4:
                        System.out.print("Id del pedido: ");
                        Long pedId = readLong();
                        System.out.println("Nuevo estado (PENDIENTE, CONFIRMADO, TERMINADO, CANCELADO) - dejar vacio para no cambiar: ");
                        String estStr = scanner.nextLine();
                        Estado estado = estStr.trim().isEmpty() ? null : Estado.valueOf(estStr.toUpperCase());
                        System.out.println("Nueva forma de pago (TARJETA, TRANSFERENCIA, EFECTIVO) - dejar vacio: ");
                        String fpStr2 = scanner.nextLine();
                        FormaPago fp2 = fpStr2.trim().isEmpty() ? null : FormaPago.valueOf(fpStr2.toUpperCase());
                        orderService.actualizarEstadoYFormaPago(pedId, estado, fp2);
                        System.out.println("Pedido actualizado");
                        break;
                    case 5:
                        System.out.print("Id del pedido a eliminar: ");
                        Long pedDel = readLong();
                        System.out.print("Confirmar (S/N): ");
                        String conf = scanner.nextLine();
                        if (conf.equalsIgnoreCase("S")) {
                            orderService.eliminar(pedDel);
                            System.out.println("Pedido eliminado");
                        } else {
                            System.out.println("Cancelado");
                        }
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Opcion invalida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static int readInteger() {
        while (true) {
            try {
                int valor = Integer.parseInt(scanner.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un numero valido: ");
            }
        }
    }

    private static long readLong() {
        while (true) {
            try {
                long valor = Long.parseLong(scanner.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un numero valido: ");
            }
        }
    }

    private static double readDouble() {
        while (true) {
            try {
                double valor = Double.parseDouble(scanner.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un numero valido: ");
            }
        }
    }
}
