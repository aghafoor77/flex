package com.ri.se.ordersemen.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ri.se.ordersemen.dto.DTOOrderSemen;
import com.ri.se.ordersemen.dto.DTOOrderSemenList;
import com.ri.se.ordersemen.dto.Message;
import com.ri.se.ordersemen.dto.OrderSemenEntityConverter;
import com.ri.se.ordersemen.persistance.OrderSemen;
import com.ri.se.ordersemen.persistance.OrderSemenList;
import com.ri.se.ordersemen.persistance.OrderSemenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/ordersemen")
@Api(value = "OrderSemen by RRB", description = "API used to provide CRUD operations for OrderSemen service ")
public class OrderSemenResource {

	private OrderSemenService ordersemenService;

	private OrderSemenEntityConverter ordersemenEntityConverter = new OrderSemenEntityConverter();

	public OrderSemenResource(OrderSemenService ordersemenService) {
		this.ordersemenService = ordersemenService;
	}

	/*
	 * @GET
	 * 
	 * @Path("/health")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @ApiOperation(value = "Returns OK report if service is running !", notes =
	 * "Returns OK report if service is running !", response = String.class)
	 * 
	 * @ApiResponses({ @ApiResponse(code = 200, message = "OK"),
	 * 
	 * @ApiResponse(code = 500, message = "Internal Server error !") }) public
	 * Response health() {
	 * 
	 * return Response.status(Response.Status.OK).entity(new
	 * Message("OK health report !")).build();
	 * 
	 * }
	 * 
	 * @GET
	 * 
	 * @Path("/deephealth")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @ApiOperation(value = "Returns OK report if service is running !", notes =
	 * "Returns OK report if service is running !", response = String.class)
	 * 
	 * @ApiResponses({ @ApiResponse(code = 200, message = "OK"),
	 * 
	 * @ApiResponse(code = 500, message = "Internal Server error !") }) public
	 * Response deephealth() {
	 * 
	 * String status = ordersemenService.performHealthCheck();
	 * if(!Objects.isNull(status)) { return
	 * Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new
	 * Message(status)).build();
	 * 
	 * } return Response.status(Response.Status.OK).entity(new
	 * Message("OK deep health report !")).build();
	 * 
	 * }
	 */
	@GET
	@Path("/{osid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [OrderSemen]!", notes = "Returns instance of OrderSemen stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("osid") final String osid) {
		OrderSemen ordersemen = this.ordersemenService.get(osid);
		if (java.util.Objects.isNull(ordersemen)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK).entity(ordersemenEntityConverter.getDTOOrderSemen(ordersemen))
					.build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored OrderSemen!", notes = "Get list of all stored OrderSemen!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<OrderSemen> list = this.ordersemenService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOOrderSemenList ordersemenListDTO = new DTOOrderSemenList();
		for (OrderSemen ordersemen : list) {
			try {
				ordersemenListDTO.add(ordersemenEntityConverter.getDTOOrderSemen(ordersemen));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(ordersemenListDTO).build();
	}

	@DELETE
	@Path("/{osid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("osid") final String osid) {
		int result = this.ordersemenService.delete(osid);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
		return Response.status(Response.Status.OK).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create OrderSemen!", notes = "Creating an instance of OrderSemen!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOOrderSemen dtoordersemen) {
		dtoordersemen.setOsid("OS-"+System.currentTimeMillis());
		STATUS status = STATUS.Pending;
		dtoordersemen.setStatus(status.value());
		OrderSemen ordersemen = null;
		try {
			ordersemen = ordersemenEntityConverter.getOrderSemen(dtoordersemen);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.ordersemenService.post(ordersemen);
		if (i > 0) {
			return Response.status(Response.Status.OK).entity(ordersemen).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@PUT
	@Path("/{osid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update OrderSemen!", notes = "Updating an instance of OrderSemen !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("osid") final String osid, @Valid DTOOrderSemen dtoordersemen) {
		dtoordersemen.setOsid(osid);
		OrderSemen ordersemen = null;
		try {
			ordersemen = ordersemenEntityConverter.getOrderSemen(dtoordersemen);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.ordersemenService.put(ordersemen);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	enum STATUS {
		Pending("Pending"), Done("Done"), Cancel("Cancel");

		String value;

		STATUS(String _value) {
			value = _value;
		}

		String value() {
			return value;
		}
	};

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/cancel/{osid}")
	@ApiOperation(value = "Cancel semen order !", notes = "Cancel semen order !", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response chnageStatus(@PathParam("osid") final String osid) {
		OrderSemen ordersemen = this.ordersemenService.get(osid);
		if (java.util.Objects.isNull(ordersemen)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		ordersemen.setOrderDate(new Date());
		ordersemen.setEmployeeID("E-234-432");
		STATUS status = STATUS.Cancel;
		ordersemen.setStatus(status.value());

		int i = this.ordersemenService.put(ordersemen);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/done/{osid}")
	@ApiOperation(value = "Chnage status when receiving response of semen order!", notes = "Chnage status when receiving response of semen order!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response chnageStatusDone(@PathParam("osid") final String osid) {
		OrderSemen ordersemen = this.ordersemenService.get(osid);
		if (java.util.Objects.isNull(ordersemen)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		ordersemen.setOrderDate(new Date());
		ordersemen.setEmployeeID("E-234-432");
		STATUS status = STATUS.Done;
		ordersemen.setStatus(status.value());

		int i = this.ordersemenService.put(ordersemen);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/doneorders")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all completed OrderSemen!", notes = "Get list of all completed OrderSemen!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response doneOrders() {
		List<OrderSemen> list = this.ordersemenService.getByStatus(STATUS.Done.value);
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		OrderSemenList ordersemenList = new OrderSemenList();
		for (OrderSemen ordersemen : list) {
			OrderSemen ordersemenT = new OrderSemen();
			ordersemenT.setBreed(ordersemen.getBreed());
			ordersemenT.setOsid(ordersemen.getOsid());
			ordersemenT.setContact(ordersemen.getContact());
			ordersemenList.add(ordersemenT);
		}
		return Response.status(Response.Status.OK).entity(ordersemenList).build();
	}

	@GET
	@Path("/cancelorders")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all cancelled OrderSemen!", notes = "Get list of all cancelled OrderSemen!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response cancelOrders() {
		List<OrderSemen> list = this.ordersemenService.getByStatus(STATUS.Cancel.value);
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		OrderSemenList ordersemenList = new OrderSemenList();
		for (OrderSemen ordersemen : list) {
			ordersemenList.add(ordersemen);
		}
		return Response.status(Response.Status.OK).entity(ordersemenList).build();
	}

	@GET
	@Path("/pendingorders")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all Pending OrderSemen!", notes = "Get list of all Pending OrderSemen!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response pendingOrders() {
		List<OrderSemen> list = this.ordersemenService.getByStatus(STATUS.Pending.value);
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		OrderSemenList ordersemenList = new OrderSemenList();
		for (OrderSemen ordersemen : list) {
			ordersemenList.add(ordersemen);
		}
		return Response.status(Response.Status.OK).entity(ordersemenList).build();
	}
}
