package ri.se.trace.persistant;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import ri.se.trace.persistant.common.AttachedResourceCO;
import ri.se.trace.persistant.common.AttachedResourceCOList;

public class AttachedResourceTransform {

	public AttachedResourceCO to(AttachedResource so) {
		AttachedResourceCO soco = new AttachedResourceCO();
		soco.setDescription(so.getDescription());
		soco.setIdentity(so.getIdentity());
		soco.setIpfshash(so.getIpfshash());
		soco.setName(so.getName());
		soco.setOwner(so.getOwner());
		soco.setType(so.getType());
		soco.setSecurityLevel(so.getSecurityLevel());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		if (!Objects.isNull(so.getUploadTime())) {
			soco.setUploadTime(sdf.format(so.getUploadTime()));
		}
		return soco;
	}

	public AttachedResource to(AttachedResourceCO soco) throws Exception {
		AttachedResource so = new AttachedResource();
		so.setDescription(soco.getDescription());
		so.setIdentity(soco.getIdentity());
		so.setIpfshash(soco.getIpfshash());
		so.setName(soco.getName());
		so.setOwner(soco.getOwner());
		so.setType(soco.getType());
		so.setSecurityLevel(soco.getSecurityLevel());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		if (!Objects.isNull(soco.getUploadTime())) {
			so.setUploadTime(sdf.parse(soco.getUploadTime()));
		}
		return so;
	}

	public AttachedResourceCOList to(List<AttachedResource> docs) {
		AttachedResourceCOList documentCOList = new AttachedResourceCOList();
		for (AttachedResource doc : docs) {
			documentCOList.add(to(doc));
		}
		return documentCOList;
	}
}
