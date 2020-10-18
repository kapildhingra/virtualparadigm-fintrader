package com.virtualparadigm.fintrader.tool.chartloader.delegate;

import java.util.ArrayList;
import java.util.List;

import com.virtualparadigm.fintrader.tool.chartloader.config.ChartLoaderConfiguration;
import com.vparadigm.shared.comp.common.logging.VParadigmLogger;

import pl.zankowski.iextrading4j.api.refdata.v1.Exchange;
import pl.zankowski.iextrading4j.api.refdata.v1.ExchangeSymbol;
import pl.zankowski.iextrading4j.client.IEXCloudClient;
import pl.zankowski.iextrading4j.client.IEXCloudTokenBuilder;
import pl.zankowski.iextrading4j.client.IEXTradingApiVersion;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.refdata.v1.ExchangeRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.refdata.v1.ExchangeSymbolsRequestBuilder;

public class InstrumentReferenceDelegateIEXImpl implements InstrumentReferenceDelegate
{
	private static final VParadigmLogger LOGGER = new VParadigmLogger(InstrumentReferenceDelegateIEXImpl.class);
	
	private IEXCloudClient iexClient;

	public InstrumentReferenceDelegateIEXImpl()
	{
		this(
			IEXTradingApiVersion.valueOf(ChartLoaderConfiguration.getIEXAPIService()), 
			ChartLoaderConfiguration.getIEXAPIPublishableToken(), 
			ChartLoaderConfiguration.getIEXAPISecretToken());
	}
	
	public InstrumentReferenceDelegateIEXImpl(IEXTradingApiVersion iexTradingApiVersion, String iexPublishableToken, String iexSecretToken)
	{
		try
		{
			this.iexClient = 
					IEXTradingClient.create(
							iexTradingApiVersion, 
							new IEXCloudTokenBuilder()
								.withPublishableToken(iexPublishableToken)
								.withSecretToken(iexSecretToken)
								.build());			
		}
		catch(Throwable t)
		{
			LOGGER.error("error initializing", t);
		}
	}
	
	@Override
	public List<String> getExchanges()
	{
        List<String> exchangeList = null;
        final List<Exchange> foundExchanges = this.iexClient.executeRequest(new ExchangeRequestBuilder().build());
        if(foundExchanges != null)
        {
        	exchangeList = new ArrayList<String>();
        	for(Exchange foundExchange : foundExchanges)
        	{
        		exchangeList.add(foundExchange.getExchange());
        	}
        }
        return exchangeList;
	}
	
//	@Override
//	public List<ExchangeVO> getExchanges()
//	{
//        List<ExchangeVO> exchangeList = null;
//        final List<Exchange> foundExchanges = this.iexClient.executeRequest(new ExchangeRequestBuilder().build());
//        if(foundExchanges != null)
//        {
//        	exchangeList = new ArrayList<ExchangeVO>();
//        	for(Exchange foundExchange : foundExchanges)
//        	{
//        		exchangeList.add(new ExchangeVO(foundExchange.getExchange(),0,0));
//        	}
//        }
//        return exchangeList;
//	}
	
	@Override
	public List<String> getSectors()
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public List<String> getIndexes()
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public List<InstrumentVO> getInstrumentsByExchange(String exchange)
	{
        List<InstrumentVO> instrumentList = null;
        final List<ExchangeSymbol> foundExchangeSymbols = 
        		this.iexClient.executeRequest(
        				new ExchangeSymbolsRequestBuilder()
        					.withExchange(exchange)
        					.build());		
		
        if(foundExchangeSymbols != null)
        {
        	instrumentList = new ArrayList<InstrumentVO>();
        	for(ExchangeSymbol foundExchangeSymbol : foundExchangeSymbols)
        	{
        		instrumentList.add(new InstrumentVO(exchange, foundExchangeSymbol.getSymbol()));
        	}
        }
		return instrumentList;
	}


	@Override
	public List<InstrumentVO> getInstrumentsBySector(String sector)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public List<InstrumentVO> getInstrumentsByIndex(String index)
	{
		throw new UnsupportedOperationException();
	}
	
	
}