package testjpa.grpc;

import com.deftype.collector.LinkOuterClass;
import com.deftype.collector.LinkServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import testjpa.domain.LinkDTO;
import testjpa.service.LinkService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyLinkServiceGrpc extends LinkServiceGrpc.LinkServiceImplBase {
    private final LinkService linkService;

    public MyLinkServiceGrpc(LinkService linkService) {
        this.linkService = linkService;
    }

    @Override
    public void addLink(LinkOuterClass.AddLinkRequest request, StreamObserver<LinkOuterClass.AddLinkReply> responseObserver) {
        //super.addLink(request, responseObserver);
        try {
            String id = linkService.addLink(request.getLink());
            responseObserver.onNext(LinkOuterClass.AddLinkReply.newBuilder().setId(id).build());
            responseObserver.onCompleted();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void findLinks(LinkOuterClass.FindLinksRequest request, StreamObserver<LinkOuterClass.FindLinksReply> responseObserver) {
        List<LinkDTO> links = linkService.findLinksByLink(request.getSearchLink());
        List<LinkOuterClass.Link> list = links
                .stream()
                .map(it -> LinkOuterClass.Link.newBuilder().setId(it.getId()).setLink(it.getLink()).build())
                .collect(Collectors.toList());
        responseObserver.onNext(LinkOuterClass.FindLinksReply.newBuilder().addAllLink(list).build());
        responseObserver.onCompleted();
    }
}
