package integrado.prog2;

import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.DetallePedido;
import integrado.prog2.enums.Rol;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.ValidacionException;
import integrado.prog2.service.CategoriaService;
import integrado.prog2.service.ProductoService;
import integrado.prog2.service.UsuarioService;
import integrado.prog2.service.PedidoService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Instanciamos todos los servicios
        CategoriaService categoriaService = new CategoriaService();
        ProductoService productoService = new ProductoService();
        UsuarioService usuarioService = new UsuarioService();
        PedidoService pedidoService = new PedidoService();

        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorias");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        menuCategorias(scanner, categoriaService);
                        break;
                    case 2:
                        menuProductos(scanner, productoService, categoriaService);
                        break;
                    case 3:
                        menuUsuarios(scanner, usuarioService);
                        break;
                    case 4:
                        menuPedidos(scanner, pedidoService, usuarioService, productoService);
                        break;
                    case 0:
                        System.out.println("Cerrando el sistema... ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opcion no valida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un numero valido.");
            }
        }
        scanner.close();
    }

    // --- SUBMENU CATEGORIAS ---
    private static void menuCategorias(Scanner scanner, CategoriaService categoriaService) {
        int opcionCat = -1;
        while (opcionCat != 0) {
            System.out.println("\n--- GESTION DE CATEGORIAS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione: ");

            try {
                opcionCat = Integer.parseInt(scanner.nextLine());

                switch (opcionCat) {
                    case 1:
                        List<Categoria> lista = categoriaService.listarCategorias();
                        if (lista.isEmpty()) System.out.println("No hay categorias cargadas.");
                        else {
                            System.out.println("\n=== LISTA DE CATEGORIAS ===");
                            for (Categoria c : lista) System.out.println(c.toString());
                        }
                        break;
                    case 2:
                        System.out.print("Nombre de la categoria: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Descripcion: ");
                        String desc = scanner.nextLine();
                        Categoria nueva = categoriaService.crearCategoria(nombre, desc);
                        System.out.println("¡Exito! Categoria creada con el ID: " + nueva.getId());
                        break;
                    case 3:
                        System.out.print("ID de la categoria a editar: ");
                        Long idEditar = Long.parseLong(scanner.nextLine());
                        System.out.print("Nuevo nombre (Enter para no modificar): ");
                        String nNombre = scanner.nextLine();
                        System.out.print("Nueva descripcion (Enter para no modificar): ");
                        String nDesc = scanner.nextLine();
                        categoriaService.editarCategoria(idEditar, nNombre, nDesc);
                        System.out.println("¡Categoria actualizada!");
                        break;
                    case 4:
                        System.out.print("ID de la categoria a eliminar: ");
                        Long idEliminar = Long.parseLong(scanner.nextLine());
                        System.out.print("¿Esta seguro? (S/N): ");
                        if (scanner.nextLine().equalsIgnoreCase("S")) {
                            categoriaService.eliminarCategoria(idEliminar);
                            System.out.println("Categoria eliminada.");
                        } else {
                            System.out.println("Operacion cancelada.");
                        }
                        break;
                    case 0:
                        System.out.println("Volviendo...");
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un numero valido.");
            } catch (ValidacionException | EntidadNoEncontradaException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // --- SUBMENU PRODUCTOS ---
    private static void menuProductos(Scanner scanner, ProductoService productoService, CategoriaService categoriaService) {
        int opcionProd = -1;
        while (opcionProd != 0) {
            System.out.println("\n--- GESTION DE PRODUCTOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione: ");

            try {
                opcionProd = Integer.parseInt(scanner.nextLine());

                switch (opcionProd) {
                    case 1:
                        List<Producto> lista = productoService.listarProductos();
                        if (lista.isEmpty()) System.out.println("No hay productos cargados.");
                        else {
                            System.out.println("\n=== LISTA DE PRODUCTOS ===");
                            for (Producto p : lista) System.out.println(p.toString());
                        }
                        break;
                    case 2:
                        System.out.print("Nombre del producto: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Descripcion: ");
                        String desc = scanner.nextLine();
                        System.out.print("Precio: ");
                        Double precio = Double.parseDouble(scanner.nextLine());
                        System.out.print("Stock inicial: ");
                        int stock = Integer.parseInt(scanner.nextLine());
                        System.out.print("Ruta de imagen: ");
                        String imagen = scanner.nextLine();
                        System.out.print("¿Esta disponible? (true/false): ");
                        Boolean disponible = Boolean.parseBoolean(scanner.nextLine());

                        System.out.print("ID de la categoria: ");
                        Long idCat = Long.parseLong(scanner.nextLine());
                        Categoria catAsignada = categoriaService.buscarPorId(idCat);
                        
                        Producto nuevo = productoService.crearProducto(nombre, desc, precio, stock, imagen, disponible, catAsignada);
                        System.out.println("¡Exito! Producto creado con ID: " + nuevo.getId());
                        break;
                    case 3:
                        System.out.print("ID del producto a editar: ");
                        Long idEditar = Long.parseLong(scanner.nextLine());
                        System.out.print("Nuevo nombre (Enter omitir): ");
                        String nNombre = scanner.nextLine();
                        System.out.print("Nueva descripcion (Enter omitir): ");
                        String nDesc = scanner.nextLine();
                        System.out.print("Nuevo precio (Enter omitir): ");
                        String strPrecio = scanner.nextLine();
                        Double nPrecio = strPrecio.isEmpty() ? null : Double.parseDouble(strPrecio);
                        System.out.print("Nuevo stock (Enter omitir): ");
                        String strStock = scanner.nextLine();
                        Integer nStock = strStock.isEmpty() ? null : Integer.parseInt(strStock);
                        System.out.print("Nuevo ID categoria (Enter omitir): ");
                        String strCat = scanner.nextLine();
                        Categoria nCat = strCat.isEmpty() ? null : categoriaService.buscarPorId(Long.parseLong(strCat));

                        productoService.editarProducto(idEditar, nNombre, nDesc, nPrecio, nStock, nCat);
                        System.out.println("¡Producto actualizado!");
                        break;
                    case 4:
                        System.out.print("ID del producto a eliminar: ");
                        Long idEliminar = Long.parseLong(scanner.nextLine());
                        System.out.print("¿Esta seguro? (S/N): ");
                        if (scanner.nextLine().equalsIgnoreCase("S")) {
                            productoService.eliminarProducto(idEliminar);
                            System.out.println("Producto eliminado.");
                        } else {
                            System.out.println("Operacion cancelada.");
                        }
                        break;
                    case 0:
                        System.out.println("Volviendo...");
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un numero valido.");
            } catch (ValidacionException | EntidadNoEncontradaException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // --- SUBMENU USUARIOS ---
    private static void menuUsuarios(Scanner scanner, UsuarioService usuarioService) {
        int opcionUsr = -1;
        while (opcionUsr != 0) {
            System.out.println("\n--- GESTION DE USUARIOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione: ");

            try {
                opcionUsr = Integer.parseInt(scanner.nextLine());

                switch (opcionUsr) {
                    case 1:
                        List<Usuario> lista = usuarioService.listarUsuarios();
                        if (lista.isEmpty()) System.out.println("No hay usuarios cargados.");
                        else {
                            System.out.println("\n=== LISTA DE USUARIOS ===");
                            for (Usuario u : lista) System.out.println(u.toString());
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
                        System.out.print("Rol (1. ADMIN / 2. USUARIO): ");
                        Rol rol = (Integer.parseInt(scanner.nextLine()) == 1) ? Rol.ADMIN : Rol.USUARIO;

                        Usuario nuevo = usuarioService.crearUsuario(nombre, apellido, mail, celular, pass, rol);
                        System.out.println("¡Exito! Usuario creado con ID: " + nuevo.getId());
                        break;
                    case 3:
                        System.out.print("ID del usuario a editar: ");
                        Long idEditar = Long.parseLong(scanner.nextLine());
                        System.out.print("Nuevo nombre (Enter omitir): ");
                        String nNombre = scanner.nextLine();
                        System.out.print("Nuevo apellido (Enter omitir): ");
                        String nApellido = scanner.nextLine();
                        System.out.print("Nuevo mail (Enter omitir): ");
                        String nMail = scanner.nextLine();
                        System.out.print("Nuevo celular (Enter omitir): ");
                        String nCelular = scanner.nextLine();
                        System.out.print("Nueva contrasena (Enter omitir): ");
                        String nPass = scanner.nextLine();
                        System.out.print("Rol (1. ADMIN / 2. USUARIO / Enter omitir): ");
                        String r = scanner.nextLine();
                        Rol nRol = r.isEmpty() ? null : (Integer.parseInt(r) == 1 ? Rol.ADMIN : Rol.USUARIO);

                        usuarioService.editarUsuario(idEditar, nNombre, nApellido, nMail, nCelular, nPass, nRol);
                        System.out.println("¡Usuario actualizado!");
                        break;
                    case 4:
                        System.out.print("ID del usuario a eliminar: ");
                        Long idEliminar = Long.parseLong(scanner.nextLine());
                        System.out.print("¿Esta seguro? (S/N): ");
                        if (scanner.nextLine().equalsIgnoreCase("S")) {
                            usuarioService.eliminarUsuario(idEliminar);
                            System.out.println("Usuario eliminado.");
                        } else {
                            System.out.println("Operacion cancelada.");
                        }
                        break;
                    case 0:
                        System.out.println("Volviendo...");
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un numero valido.");
            } catch (ValidacionException | EntidadNoEncontradaException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // --- SUBMENU PEDIDOS (EL CARRITO DE COMPRAS) ---
    private static void menuPedidos(Scanner scanner, PedidoService pedidoService, UsuarioService usuarioService, ProductoService productoService) {
        int opcionPed = -1;
        while (opcionPed != 0) {
            System.out.println("\n--- GESTION DE PEDIDOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear nuevo pedido");
            System.out.println("3. Actualizar estado / Pago");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione: ");

            try {
                opcionPed = Integer.parseInt(scanner.nextLine());

                switch (opcionPed) {
                    case 1:
                        List<Pedido> lista = pedidoService.listarPedidos();
                        if (lista.isEmpty()) {
                            System.out.println("No hay pedidos registrados.");
                        } else {
                            System.out.println("\n=== LISTA DE PEDIDOS ===");
                            for (Pedido p : lista) {
                                System.out.println(p.toString());
                                // Mostramos un resumen de lo que tiene adentro
                                for (DetallePedido dp : p.getDetalles()) {
                                    System.out.println("   -> " + dp.toString());
                                }
                            }
                        }
                        break;

                    case 2:
                        System.out.print("Ingrese el ID del Usuario que realiza el pedido: ");
                        Long idUsr = Long.parseLong(scanner.nextLine());
                        Usuario cliente = usuarioService.buscarPorId(idUsr);

                        System.out.print("Forma de pago (1. TARJETA, 2. TRANSFERENCIA, 3. EFECTIVO): ");
                        int fpOpc = Integer.parseInt(scanner.nextLine());
                        FormaPago fp = (fpOpc == 1) ? FormaPago.TARJETA : (fpOpc == 2) ? FormaPago.TRANSFERENCIA : FormaPago.EFECTIVO;

                        // Creamos el pedido en estado "borrador" (ID null porque lo asigna el Service al guardar)
                        Pedido nuevoPedido = new Pedido(null, cliente, fp);

                        // Bucle del carrito de compras
                        boolean agregando = true;
                        while (agregando) {
                            System.out.print("\nIngrese el ID del Producto a agregar: ");
                            Long idProd = Long.parseLong(scanner.nextLine());
                            Producto prod = productoService.buscarPorId(idProd);

                            System.out.print("Cantidad a llevar: ");
                            int cantidad = Integer.parseInt(scanner.nextLine());

                            if (cantidad <= 0) {
                                throw new ValidacionException("Error: La cantidad debe ser mayor a 0.");
                            }
                            if (cantidad > prod.getStock()) {
                                throw new ValidacionException("Error: Falta de stock. Stock actual: " + prod.getStock());
                            }

                            // Agregamos el detalle usando el método obligatorio del UML
                            nuevoPedido.addDetallePedido(cantidad, prod.getPrecio(), prod);
                            
                            // Descontamos el stock provisoriamente
                            prod.setStock(prod.getStock() - cantidad);

                            System.out.print("¿Desea agregar otro producto a este pedido? (S/N): ");
                            if (scanner.nextLine().equalsIgnoreCase("N")) {
                                agregando = false;
                            }
                        }

                        // Guardamos el pedido final en la lista
                        pedidoService.guardarPedido(nuevoPedido);
                        System.out.println("\n¡Exito! Pedido registrado exitosamente.");
                        System.out.println("ID del Pedido: " + nuevoPedido.getId());
                        System.out.println("Monto Total Calculado: $" + nuevoPedido.getTotal());
                        break;

                    case 3:
                        System.out.print("Ingrese ID del pedido a modificar: ");
                        Long idEditar = Long.parseLong(scanner.nextLine());
                        
                        System.out.println("Nuevo Estado (1. PENDIENTE, 2. CONFIRMADO, 3. TERMINADO, 4. CANCELADO / 0 para no cambiar): ");
                        int opcEst = Integer.parseInt(scanner.nextLine());
                        Estado nEst = null;
                        if (opcEst == 1) nEst = Estado.PENDIENTE;
                        if (opcEst == 2) nEst = Estado.CONFIRMADO;
                        if (opcEst == 3) nEst = Estado.TERMINADO;
                        if (opcEst == 4) nEst = Estado.CANCELADO;

                        System.out.println("Nueva Forma de Pago (1. TARJETA, 2. TRANSFERENCIA, 3. EFECTIVO / 0 para no cambiar): ");
                        int opcPag = Integer.parseInt(scanner.nextLine());
                        FormaPago nPag = null;
                        if (opcPag == 1) nPag = FormaPago.TARJETA;
                        if (opcPag == 2) nPag = FormaPago.TRANSFERENCIA;
                        if (opcPag == 3) nPag = FormaPago.EFECTIVO;

                        pedidoService.actualizarPedido(idEditar, nEst, nPag);
                        System.out.println("¡Pedido actualizado con exito!");
                        break;

                    case 4:
                        System.out.print("Ingrese ID del pedido a eliminar: ");
                        Long idEliminar = Long.parseLong(scanner.nextLine());
                        System.out.print("¿Esta seguro? (S/N): ");
                        if (scanner.nextLine().equalsIgnoreCase("S")) {
                            pedidoService.eliminarPedido(idEliminar);
                            System.out.println("Pedido dado de baja.");
                        } else {
                            System.out.println("Operacion cancelada.");
                        }
                        break;

                    case 0:
                        System.out.println("Volviendo al menu principal...");
                        break;

                    default:
                        System.out.println("Opcion no valida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un numero valido.");
            } catch (ValidacionException | EntidadNoEncontradaException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Error inesperado al gestionar el pedido: " + e.getMessage());
            }
        }
    }
}