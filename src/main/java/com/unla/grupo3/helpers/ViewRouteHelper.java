package com.unla.grupo3.helpers;

public class ViewRouteHelper {
	/**** Views ****/
	//HOME
	public final static String INDEX = "home/index";
	public final static String HELLO = "home/hello";
	public final static String ADM = "home/administrar";
	public final static String PRODUCTVIEW = "home/productview";
	//USER
	public final static String USER_LOGIN = "user/login";
	public final static String USER_LOGOUT = "user/logout";
	
	//Productos
	public final static String PRODUCTS = "producto/lista";
	public final static String INDI = "producto/individual";
	public final static String NEW_PRODUCTO = "producto/nuevo";
	public final static String ADMIN_PRODUCTO = "producto/administrar";
	
	
	//Pedidos
	public final static String PEDIDOS = "pedido/lista";
	public final static String INDI_PEDIDO = "pedido/individual";
	
	//Stock
	public final static String STOCKS ="stock/lista";
	public final static String INDI_STOCK ="stock/individual";
	
	//Orden de compra
	public final static String ORDERS = "ordendecompra/lista";
	public final static String INDI_ORDER = "ordendecompra/individual";
	public final static String NEW_ORDER = "ordendecompra/nueva";

	/**** Redirects ****/
	public final static String ROUTE = "/index";

	public final static String ROUTE_INDI = "/producto";

	public final static String ROUTE_INDI_ODC = "/ordendecompra";
	
	public final static String RUTA_PRODUCTS = "lista";
	
	public final static String ROUTE_LOTE = "/lote";
	
	public final static String ROUTE_ORDERS = "/pedido/pedidosrealizados";


	
	//Lote
	
	public final static String LOTE = "/Lote/";
	public final static String LOTES = "/Lote/lista";
	public final static String INDI_LOTE = "/Lote/individual";

	
	
	
	
}
