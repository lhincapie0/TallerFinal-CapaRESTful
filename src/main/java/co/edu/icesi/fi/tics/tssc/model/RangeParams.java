package co.edu.icesi.fi.tics.tssc.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class RangeParams {


	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern =  "yyyy-MM-dd")
	private LocalDate date1;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern =  "yyyy-MM-dd")
	private LocalDate date2;
	
	
	public RangeParams()
	{
		
	}
	
	
	
}
