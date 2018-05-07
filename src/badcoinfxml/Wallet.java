package badcoinfxml;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.*;

public class Wallet {	
    public PrivateKey privateKey;
    public PublicKey publicKey;

    public HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();

    public Wallet() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        }catch(Exception ex) {
        }
    }

    public float getBalance() {
        float total = 0;	
        for (Map.Entry<String, TransactionOutput> item: BlockChain.UTXOs.entrySet()){
            TransactionOutput UTXO = item.getValue();
            if(UTXO.checkOwner(publicKey)) { 
                UTXOs.put(UTXO.id,UTXO);
                total += UTXO.value ; 
            }
        }  
        return total;
    }

    public Transaction makeTransaction(PublicKey recipient,float amountToBeTransferred) {
        if(getBalance() < amountToBeTransferred) {
            return null;
        }
        ArrayList<TransactionInput> inputs = new ArrayList<>();
        float total = 0;
        for (Map.Entry<String, TransactionOutput> item: UTXOs.entrySet()){
            TransactionOutput temp = item.getValue();
            total += temp.value;
            inputs.add(new TransactionInput(temp.id));
            if(total > amountToBeTransferred) 
                break;
        }

        Transaction newTransaction = new Transaction(publicKey, recipient , amountToBeTransferred, inputs);
        newTransaction.generateSignature(privateKey);
        for(TransactionInput input: inputs){
            UTXOs.remove(input.transactionOutputId);
        }
        return newTransaction;
    }	
}
