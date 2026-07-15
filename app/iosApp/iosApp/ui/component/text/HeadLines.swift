import SwiftUI

struct HeadLines: View {
    let title: String

    var body: some View {
        Text(title)
            .font(.system(size: 34, weight: .bold))
            .kerning(5)
            .multilineTextAlignment(.center)
            .lineLimit(1)
            
    }
}

struct HeadLines_Previews: PreviewProvider {
    static var previews: some View {
        HeadLines(title: "Hello World")
            .previewLayout(.sizeThatFits)
            .padding()
    }
}
